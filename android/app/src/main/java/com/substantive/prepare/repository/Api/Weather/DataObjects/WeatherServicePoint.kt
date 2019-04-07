package com.substantive.prepare.repository.Api.Weather.DataObjects

import java.util.*

data class WeatherServicePoint (
    val properties : WeatherServicePointProperties,
    val geometry : WeatherServiceGeometry
)

data class WeatherServicePointProperties (
    val cwa : String,
    val gridX : Int,
    val gridY : Int
)

