package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceForecast(
    val properties : WeatherServiceForecastProperties
)

data class WeatherServiceForecastProperties(
    val updated : String,
    val elevation : WeatherServiceElevation,
    val periods : List<WeatherServiceForecastPeriod>
)
data class WeatherServiceForecastPeriod (
    val number : Int,
    val name : String,
    val tempuraure : Int,
    val tempuratureUnit : String,
    val windSpeed : String,
    val windDirection : String,
    val shortForecast : String,
    val detailedForecast : String

)