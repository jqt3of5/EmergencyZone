package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceZones(
    val features : Array<WeatherServiceZone> = arrayOf()
)

data class WeatherServiceZone (
    val properties : WeatherServiceZoneProperties = WeatherServiceZoneProperties()
)

data class WeatherServiceZoneProperties (
    val id : String = "",
    val type : String = "",
    val name : String = "",
    val state : String? = "",
    val cwa : Array<String> = arrayOf()

)