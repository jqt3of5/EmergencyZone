package com.emergencyzone.emergencyzone.home

class WeatherModel(var temp: Float, var humidity : Float, var location : String,  var type : WeatherType, var hasAlerts : Boolean) {
    enum class WeatherType { Sunny, Cloudy, Rainy, Snowy, Windy }
}