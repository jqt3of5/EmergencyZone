package com.substantive.prepare.repository.Api.Weather

import com.substantive.prepare.repository.Api.Weather.DataObjects.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

//https://www.ncdc.noaa.gov/cdo-web/api/v2/{endpoint}?
//https://api.weather.gov/
interface WeatherApi
{
    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/alerts/active/zone/{zone}")
    //Let's use Live data instead of RxJava!
    fun getAlertByZone(@Path("zone") zone : String) : Call<WeatherServiceAlerts>

    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/alerts/active/count")
    fun getAlertCounts() : Call<AlertCountsByLocation>

    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/zones/forecast")
    fun getForecastZones() : Call<WeatherServiceZones>


    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("/zones/forecast/{zone}/stations")
    fun getStationsInZone(@Path("zone") zoneId : String) : Call<WeatherServiceStations>

    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("points/{lat},{lon}")
    fun getPoint(@Path("lat") latitude : Float, @Path("lon")longitude : Float) : Call<WeatherServicePoint>

    @Headers("User-Agent: jqt3of5@gmail.com", "Accept: application/vnd.github.v3.full+json")
    @GET("gripdpoints/{wfo}/{x},{y}/forecast")
    fun getForecast(@Path("wfo") wfo: String, @Path("x") x: Int, @Path("y") y: Int) : Call<WeatherServiceForecast>
}