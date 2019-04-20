package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceForecast(
    val properties : WeatherServiceForecastProperties = WeatherServiceForecastProperties()
)

data class WeatherServiceForecastProperties(
    val updated : String = "",
    val elevation : WeatherServiceElevation = WeatherServiceElevation(),
    val periods : List<WeatherServiceForecastPeriod> = listOf()
)
data class WeatherServiceForecastPeriod (
    val number : Int = -1,
    val name : String = "",
    val tempurature : Int = 0,
    val tempuratureUnit : String = "",
    val windSpeed : String = "",
    val windDirection : String = "",
    val shortForecast : String = "",
    val detailedForecast : String = ""

)