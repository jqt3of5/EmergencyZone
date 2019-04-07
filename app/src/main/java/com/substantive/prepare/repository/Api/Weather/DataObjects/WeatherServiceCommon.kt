package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceGeometry (
    val type : String,
    val coordinates: List<Float>
)

data class WeatherServiceElevation (
    val value : Float,
    val unitCode : String
)