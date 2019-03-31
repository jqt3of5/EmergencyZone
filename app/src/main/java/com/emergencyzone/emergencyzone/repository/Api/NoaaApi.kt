package com.emergencyzone.emergencyzone.repository.Api


import com.emergencyzone.emergencyzone.repository.Api.DataObjects.AlertCountsByLocation
import com.emergencyzone.emergencyzone.repository.Api.DataObjects.WeatherServiceZone
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

//https://www.ncdc.noaa.gov/cdo-web/api/v2/{endpoint}

interface NoaaApi
{
}

//https://api.weather.gov/
interface WeatherApi
{
    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/alerts/active/zone/{zone}")
    //Let's use Live data instead of RxJava!
    fun getAlertByZone(@Path("zone") zone : String) : Call<WeatherServiceZone>


    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/alerts/active/area/{area}")
    fun getAlertByArea(@Path("area") area : String) : Call<WeatherServiceZone>


    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/alerts/active/count")
    fun getAlertCounts() : Call<AlertCountsByLocation>

    //@Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    //@GET("/alerts/active/")
    //fun getAllActiveAlerts() : Observable<ZoneAlert>

    //@Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    //@GET("/alerts/active/region/{region}")
    //fun getAlertByRegion(@Path("region") region : String) : Observable<ZoneAlert>

}