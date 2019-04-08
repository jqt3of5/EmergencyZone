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
        val db = MainDatabase.getInstance()
        val dao = db.zones()
        if (dao.getZoneCount() == 0)
        {
            downloadZones {
                MainDatabase.runInAsyncTransaction {
                    val dao = zones()
                    it?.features?.forEach { zone ->
                        var properties = zone.properties
                        //TODO: First CWA? Does that work?
                        val entity = ZoneEntity(properties.id,properties.type, properties.name, properties.state, properties.cwa.first())
                        dao.Insert(entity)
                    }
                }

            }
        }

        return dao.getAllZones()
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

    fun getStationsForZone(zoneId : String) : LiveData<StationEntity>
    {

    }

    private fun downloadStationsForZone(zoneId : String, callback: (WeatherServiceStations?) -> Unit)
    {
        Netwo
    }

    fun getWeatherForZone(zoneId : String) : LiveData<ForecastEntity>
    {
        val db = MainDatabase.getInstance()
        var zone = db.zones().getZoneForId(zoneId)
        downloadForecastForZone(zone)

        return db.forecasts().getForecastForZone(zoneId)
    }

    fun downloadForecastForZone(zone : ZoneEntity)
    {
        NetworkingFactory.api<WeatherApi>
        {
            getForecast(zone.zoneType, zone.zoneId).enqueue(object : Callback<WeatherServiceForecast> {
                override fun onFailure(call: Call<WeatherServiceForecast>, t: Throwable) {

                }

                override fun onResponse(call: Call<WeatherServiceForecast>, response: Response<WeatherServiceForecast>) {
                    response.body()?.let {
                        MainDatabase.runInAsyncTransaction {



                            forecasts().Insert()
                        }
                    }
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