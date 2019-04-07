package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceStations (
    val features : List<WeatherServiceStation>
)

data class WeatherServiceStation (
    val geometry : WeatherServiceGeometry,
    val properties : WeatherServiceStationProperties
)
data class WeatherServiceStationProperties (
    val stationIdentifier : String,
    val name : String,
    val timeZone : String
)
