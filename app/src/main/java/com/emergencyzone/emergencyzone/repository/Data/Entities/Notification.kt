package com.emergencyzone.emergencyzone.repository.Data.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = Notification.TABLE_NAME, primaryKeys = ["table", "foreign_key"])
class Notification(
        var table : String,
        var foreign_key : Long,
        var date : Date?
) {
    companion object {
        const val TABLE_NAME : String = "Notifications"
    }
}