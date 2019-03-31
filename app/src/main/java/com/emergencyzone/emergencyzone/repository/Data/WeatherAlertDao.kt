package com.emergencyzone.emergencyzone.repository.Data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.emergencyzone.emergencyzone.repository.Data.Entities.WeatherAlert

import io.reactivex.Single

@Dao
interface WeatherAlertDao {

    @Query("SELECT * FROM " + WeatherAlert.TABLE_NAME)
    fun selectAllAlerts() : LiveData<List<WeatherAlert>>

    @Query("SELECT * FROM " + WeatherAlert.TABLE_NAME + " WHERE zoneCode = :zone")
    fun selectByZoneCode(zone : String) : LiveData<List<WeatherAlert>>

    @Query("SELECT * FROM " + WeatherAlert.TABLE_NAME + " WHERE id = :id")
    fun selectById(id : Long) : WeatherAlert?

    @Query("SELECT * FROM " + WeatherAlert.TABLE_NAME + " WHERE id IN(:ids)")
    fun selectByIds(ids : List<Long>) : List<WeatherAlert>

    @Query ("SELECT count(id) FROM " + WeatherAlert.TABLE_NAME + " WHERE url = :url")
    fun alertCountForUrl(url : String) : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(alert : WeatherAlert) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(alerts : List<WeatherAlert>) : List<Long>

}