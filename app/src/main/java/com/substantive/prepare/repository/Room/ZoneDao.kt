package com.substantive.prepare.repository.Room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.substantive.prepare.repository.Room.Entities.ZoneEntity

@Dao
interface ZoneDao {
    @Query("Select * from " + ZoneEntity.TABLE_NAME)
    fun getAllZones() : List<ZoneEntity>

    @Query("Select * from " + ZoneEntity.TABLE_NAME + " where zoneId = :zoneId")
    fun getZoneForId(zoneId : String) : ZoneEntity

    @Query("Select * from " + ZoneEntity.TABLE_NAME + " where state = :state")
    fun getZonesForState(state : String) : List<ZoneEntity>

    @Insert
    fun Insert(zone : ZoneEntity)

}