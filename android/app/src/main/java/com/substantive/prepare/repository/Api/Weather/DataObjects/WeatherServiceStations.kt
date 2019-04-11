package com.substantive.prepare.repository.Api.Weather.DataObjects

data class WeatherServiceStations (
    val features : List<WeatherServiceStation> = listOf()
)

data class WeatherServiceStation (
    val geometry : WeatherServiceGeometry = WeatherServiceGeometry(),
    val properties : WeatherServiceStationProperties = WeatherServiceStationProperties()
)
data class WeatherServiceStationProperties (
    val stationIdentifier : String = "",
    val name : String = "",
    val timeZone : String = "",
    val elevation : WeatherServiceElevation = WeatherServiceElevation()
)
