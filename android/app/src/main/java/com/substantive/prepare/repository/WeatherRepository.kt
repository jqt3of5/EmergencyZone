package com.substantive.prepare.repository

import android.arch.lifecycle.LiveData
import com.substantive.prepare.repository.Api.NetworkingFactory
import com.substantive.prepare.repository.Api.Weather.DataObjects.*
import com.substantive.prepare.repository.Api.Weather.WeatherApi
import com.substantive.prepare.repository.Room.Entities.ForecastEntity
import com.substantive.prepare.repository.Room.Entities.StationEntity
import com.substantive.prepare.repository.Room.Entities.WeatherAlert
import com.substantive.prepare.repository.Room.Entities.ZoneEntity
import com.substantive.prepare.repository.Room.MainDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    //region Zones
    fun getForecastZones() : LiveData<List<ZoneEntity>>
    {
        MainDatabase.runInAsyncTransaction {

            val dao = zones()
            if (dao.getZoneCount() == 0)
            {
                downloadZones {
                    MainDatabase.runInAsyncTransaction {

                        it?.features?.forEach { zone ->
                            var properties = zone.properties
                            //TODO: First CWA? Does that work?
                            val entity = ZoneEntity(properties.id,properties.type, properties.name, properties.state?: "", properties.cwa.first())
                            dao.Insert(entity)
                        }
                    }
                }
            }
        }
        return MainDatabase.getInstance().zones().getAllZones()
    }

    private fun downloadZones(callback : (WeatherServiceZones?) -> Unit)
    {
        NetworkingFactory.api<WeatherApi>
        {
            getForecastZones().enqueue(object : Callback<WeatherServiceZones> {
                override fun onFailure(call: Call<WeatherServiceZones>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<WeatherServiceZones>, response: Response<WeatherServiceZones>) {
                    callback(response.body())
                }
            })
        }
    }
//endregion

    //region Stations
    fun getStationsForZone(zoneId : String) : LiveData<List<StationEntity>>
    {
        MainDatabase.runInAsyncTransaction {

            val dao = stations()
            if (dao.getStationCountForZone(zoneId) == 0)
            {
                downloadStationsForZone(zoneId) { stations ->
                        MainDatabase.runInAsyncTransaction {

                            stations?.features?.forEach {
                                val props = it.properties
                                val geometry = it.geometry
                                val entity = StationEntity(
                                    props.stationIdentifier, zoneId, props.name,
                                    props.elevation.value, props.elevation.unitCode,
                                    geometry.coordinates[0], geometry.coordinates[1]
                                )
                                dao.insert(entity)
                            }
                        }
                    }
                }
            }

        return MainDatabase.getInstance().stations().getStationsForZone(zoneId)
    }

    private fun downloadStationsForZone(zoneId : String, callback: (WeatherServiceStations?) -> Unit)
    {
        NetworkingFactory.api<WeatherApi> {
            getStationsInZone(zoneId).enqueue(object : Callback<WeatherServiceStations?> {
                override fun onFailure(call: Call<WeatherServiceStations?>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(
                    call: Call<WeatherServiceStations?>,
                    response: Response<WeatherServiceStations?>
                ) {
                    callback(response.body())
                }
            })
        }
    }
//endregion

    fun getWeatherForStation(stationId : String) : LiveData<ForecastEntity>
    {
        val db = MainDatabase.getInstance()
        val station = db.stations().getStationById(stationId)

        downloadPointData(station.latitude, station.longitude) { point ->
            point?.properties?.let {
                downloadForecast(it.cwa, it.gridX, it.gridY){forecast ->
                    MainDatabase.runInAsyncTransaction {
                        val dao = forecasts()
                        forecast?.properties?.periods?.forEach { period ->
                            val entity = ForecastEntity(stationId, point.properties.cwa, point.properties.gridX, point.properties.gridY,
                                period.number, period.name,"","",
                                period.tempurature, period.tempuratureUnit,
                                period.windSpeed, period.windDirection,
                                period.shortForecast, period.detailedForecast, ""
                                )
                            dao.Insert(entity)
                        }
                    }
                }
            }
        }

        return db.forecasts().getForecastForStation(stationId)
    }

    fun downloadPointData(lat : Float, lon: Float, callback : (WeatherServicePoint?)->Unit)
    {
        NetworkingFactory.api<WeatherApi>
        {
            getPoint(lat, lon).enqueue(object : Callback<WeatherServicePoint> {
                override fun onFailure(call: Call<WeatherServicePoint>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<WeatherServicePoint>, response: Response<WeatherServicePoint>) {
                    callback(response.body())
                }
            })
        }
    }

    fun downloadForecast(wfo : String, x : Int, y : Int,callback : (WeatherServiceForecast?) -> Unit)
    {
        NetworkingFactory.api<WeatherApi>
        {
            getForecast(wfo, x, y).enqueue(object : Callback<WeatherServiceForecast> {
                override fun onFailure(call: Call<WeatherServiceForecast>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<WeatherServiceForecast>, response: Response<WeatherServiceForecast>) {
                    callback(response.body())
                }
            })
        }
    }


    fun getAlertsForZone(zoneCode : String) : LiveData<List<WeatherAlert>>
    {
        downloadAlertForZone(zoneCode)
        val db = MainDatabase.getInstance()
        return db.weatherAlerts().selectByZoneCode(zoneCode)
    }


    fun downloadAlertForZone(zoneCode : String)
    {
        NetworkingFactory.api<WeatherApi>
        {
            getAlertByZone(zoneCode)
                    .enqueue(object : Callback<WeatherServiceAlerts> {
                        override fun onFailure(call: Call<WeatherServiceAlerts>?, t: Throwable?) {
                        }

                        override fun onResponse(call: Call<WeatherServiceAlerts>?, response: Response<WeatherServiceAlerts>?) {
                            response?.body()?.let {
                                MainDatabase.runInAsyncTransaction {
                                    val alerts = createAlerts(it).filter {
                                        weatherAlerts().alertCountForUrl(it.url) == 0
                                    }
                                    val ids: List<Long> = weatherAlerts().insertAll(alerts)
                                    alerts.zip(ids) { alert, id ->
                                        alert.id = id
                                    }
                                }
                            }
                        }
                    })
        }
    }

    fun createAlerts(zone : WeatherServiceAlerts) : List<WeatherAlert>
    {
         return zone.features!!.map { feature ->
            feature.properties!!.let {
                WeatherAlert(feature.id, zone.zoneCode,
                    it.areaDesc,
                    it.headline,
                    it.description,
                    it.severity,
                    it.certainty,
                    it.event,
                    it.instruction,
                    it.sent,
                    it.effective,
                    it.expires,
                    it.ends)
            }
        }
    }

}