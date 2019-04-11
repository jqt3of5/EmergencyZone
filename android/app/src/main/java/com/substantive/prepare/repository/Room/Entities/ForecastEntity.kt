package com.substantive.prepare.repository.Room.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(tableName = ForecastEntity.TABLE_NAME, primaryKeys = ["stationId", "number"])
data class ForecastEntity(
    @ForeignKey(entity = ZoneEntity::class, parentColumns = ["stationId"], childColumns = ["stationId"])
    val stationId: String,
    val cwa: String,
    val gridX: Int,
    val gridY: Int,
    val number: Int,
    val name: String,
    val startTime: String,
    val endTime: String,
    val tempurature: Int,
    val tempuratureUnit: String,
    val windSpeed: String,
    val windDirection: String,
    val shortFrecast: String,
    val detailedForecast: String,
    val icon: String
) {
    companion object {
        const val TABLE_NAME = "forecast"
    }
}