package com.substantive.prepare.repository.Data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.substantive.prepare.repository.Data.Entities.EmergencyZoneNotification


@Dao
interface EmergencyZoneNotificationDao {

    @Query("SELECT * FROM " + EmergencyZoneNotification.TABLE_NAME + " WHERE id = :id")
    fun selectById(id : Long) : EmergencyZoneNotification?

    @Insert
    fun insert(post : EmergencyZoneNotification)

    @Insert
    fun insertAll(post : List<EmergencyZoneNotification>)
}