package com.substantive.prepare.repository

import android.arch.lifecycle.LiveData
import com.substantive.prepare.repository.Api.DataObjects.WeatherServiceZone
import com.substantive.prepare.repository.Api.NetworkingFactory
import com.substantive.prepare.repository.Api.WeatherApi
import com.substantive.prepare.repository.Data.Entities.WeatherAlert
import com.substantive.prepare.repository.Data.MainDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoaaAlertsRepository {

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
                    .enqueue(object : Callback<WeatherServiceZone> {
                        override fun onFailure(call: Call<WeatherServiceZone>?, t: Throwable?) {
                        }

                        override fun onResponse(call: Call<WeatherServiceZone>?, response: Response<WeatherServiceZone>?) {
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

    fun createAlerts(zone : WeatherServiceZone) : List<WeatherAlert>
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