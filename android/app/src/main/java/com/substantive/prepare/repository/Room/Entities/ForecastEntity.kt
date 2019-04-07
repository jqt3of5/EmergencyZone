package com.substantive.prepare.repository.Room.Entities

import android.arch.persistence.room.Entity

@Entity(tableName = ForecastEntity.TABLE_NAME, primaryKeys = ["zoneId", "stationId", "number"])
data class ForecastEntity(
    val zoneId : String,
    val stationId : String,
    val cwa : String,
    val gridX : Int,
    val gridY : Int,
    val number : Int,
    val name : String,
    val startTime : String,
    val endTime : String,
    val tempurature : String,
    val tempuratureUnit : String,
    val windSpeed : String,
    val windDirection : String,
    val shortFrecast : String,
    val detailedForecast : String,
    val icon : String
) {
    companion object {
        const val TABLE_NAME = "forecast"
    }
}