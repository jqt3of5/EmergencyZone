package com.substantive.prepare.repository.Room.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = com.substantive.prepare.repository.Room.Entities.EmergencyZoneNotification.TABLE_NAME)
class EmergencyZoneNotification(
        val title : String,
        val description: String,
        val url : String)
{
        companion object {
            const val TABLE_NAME : String = "EmergencyZoneNotifications"
        }

    @PrimaryKey (autoGenerate = true)
    var id : Long = 0
}