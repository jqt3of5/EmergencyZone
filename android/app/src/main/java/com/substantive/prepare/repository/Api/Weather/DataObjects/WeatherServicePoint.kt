package com.substantive.prepare.repository.Api.Weather.DataObjects

import java.util.*

data class WeatherServicePoint (
    val properties : WeatherServicePointProperties = WeatherServicePointProperties(),
    val geometry : WeatherServiceGeometry = WeatherServiceGeometry()
)

data class WeatherServicePointProperties (
    val cwa : String = "",
    val gridX : Int = -1,
    val gridY : Int = -1
)

