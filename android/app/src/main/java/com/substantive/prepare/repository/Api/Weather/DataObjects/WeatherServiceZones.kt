package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceZones(
    val features : Array<WeatherServiceZone>
)

data class WeatherServiceZone (
    val properties : WeatherServiceZoneProperties
)

data class WeatherServiceZoneProperties (
    val id : String,
    val type : String,
    val name : String,
    val state : String,
    val cwa : Array<String>

)