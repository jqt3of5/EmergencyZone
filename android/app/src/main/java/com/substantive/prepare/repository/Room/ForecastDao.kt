package com.substantive.prepare.repository.Room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.substantive.prepare.repository.Room.Entities.ForecastEntity

@Dao
interface ForecastDao {

    @Query("Select * from " + ForecastEntity.TABLE_NAME + " where stationId = :stationId")
    fun getForecastForStation(stationId : String) : LiveData<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(entity : ForecastEntity)
}