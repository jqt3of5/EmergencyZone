package com.substantive.prepare.repository.Room.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = ZoneEntity.TABLE_NAME)
class ZoneEntity (
    @PrimaryKey
    val zoneId : String,
    val zoneType : String,
    val zoneName : String,
    val state : String,
    val cwa : String //Weather office
    ) {

    companion object {
        const val TABLE_NAME : String = "Zones"
    }

    override fun toString(): String {
        return zoneName + ", " + state
    }
}