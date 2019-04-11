package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceGeometry (
    val type : String = "",
    val coordinates: List<Float> = listOf()
)

data class WeatherServiceElevation (
    val value : Float = 0f,
    val unitCode : String = ""
)