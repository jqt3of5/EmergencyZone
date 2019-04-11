package com.substantive.prepare.repository.Room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.substantive.prepare.repository.Room.Entities.StationEntity

@Dao
interface StationDao {

    @Insert
    fun insert(station : StationEntity)

    @Query("select * from " + StationEntity.TABLE_NAME + " where zoneId = :zoneId")
    fun getStationsForZone(zoneId : String) : LiveData<List<StationEntity>>

    @Query("select count(*) from " + StationEntity.TABLE_NAME + " where zoneId = :zoneId")
    fun getStationCountForZone(zoneId : String) : Int

    @Query("select * from " + StationEntity.TABLE_NAME + " where stationId = :stationId")
    fun getStationById(stationId: String) : StationEntity

}