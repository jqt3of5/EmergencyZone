package com.emergencyzone.emergencyzone.repository.Data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.emergencyzone.emergencyzone.repository.Data.Entities.Notification

@Dao
interface NotificationDao {

    @Query("SELECT * FROM " + Notification.TABLE_NAME)
    fun getAllNotifications() : LiveData<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(notification : Notification)
}