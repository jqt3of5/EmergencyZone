package com.substantive.prepare.repository.Data.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = com.substantive.prepare.repository.Data.Entities.EmergencyZoneNotification.TABLE_NAME)
class EmergencyZoneNotification(
        val title : String,
        val description: String,
        val url : String) : INotifiableEntity
{
        companion object {
            const val TABLE_NAME : String = "EmergencyZoneNotifications"
        }

    @PrimaryKey (autoGenerate = true)
    var id : Long = 0
}