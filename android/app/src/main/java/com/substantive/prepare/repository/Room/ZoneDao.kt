package com.substantive.prepare.repository.Room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.substantive.prepare.repository.Room.Entities.ZoneEntity

@Dao
interface ZoneDao {


    @Query("Select count(*) from " + ZoneEntity.TABLE_NAME)
    fun getZoneCount() : Int

    @Query("Select * from " + ZoneEntity.TABLE_NAME)
    fun getAllZones() : LiveData<List<ZoneEntity>>

    @Query("Select * from " + ZoneEntity.TABLE_NAME + " where zoneId = :zoneId")
    fun getZoneForId(zoneId : String) : ZoneEntity

    @Query("Select * from " + ZoneEntity.TABLE_NAME + " where state = :state")
    fun getZonesForState(state : String) : List<ZoneEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(zone : ZoneEntity)

}