package com.substantive.prepare.repository.Data.Entities

import android.arch.persistence.room.Entity
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