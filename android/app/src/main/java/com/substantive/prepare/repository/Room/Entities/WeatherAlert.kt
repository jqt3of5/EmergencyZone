package com.substantive.prepare.repository.Room.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = WeatherAlert.TABLE_NAME)
class WeatherAlert(var url : String,
                   var zoneCode: String,
                   var areaDesc : String,
                   var headline : String,
                   var description : String,
                   var severity : String,
                   var certainty : String,
                   var event : String,
                   var instruction: String,
                   var sent : Date?,
                   var effective: Date?,
                   var expires : Date?,
                   var ends : Date?)
{
    companion object {
        const val TABLE_NAME : String = "WeatherAlerts"
    }

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}