package com.substantive.prepare.repository.Room.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = StationEntity.TABLE_NAME)
data class StationEntity (
    @PrimaryKey
    val stationId : String,
    @ForeignKey(entity = ZoneEntity::class, parentColumns = ["zoneId"],childColumns = ["zoneId"])
    val zoneId : String,
    val name : String,
    val elevation : Float,
    val elevationUnit : String,
    val latitude : Float,
    val longitude : Float
){
    companion object {
        const val TABLE_NAME = "stations"
    }
}