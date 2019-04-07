package com.substantive.prepare.repository.Room

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.substantive.prepare.repository.Room.Entities.StationEntity

interface StationDao {

    @Insert
    fun insert(station : StationEntity)

    @Query("select * from " + StationEntity.TABLE_NAME + " where zoneId = :zoneId")
    fun getStationsForZone(zoneId : String) : List<StationEntity>
}