package com.richaa2.mappdp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert
    suspend fun insertLocation(location: LocationInfoEntity)

    @Update
    suspend fun updateLocation(location: LocationInfoEntity)

    @Delete
    suspend fun deleteLocation(location: LocationInfoEntity)

    @Query("SELECT * FROM locations_info WHERE id = :id")
    suspend fun getLocationById(id: Long): LocationInfoEntity?

    @Query("SELECT * FROM locations_info")
    fun getAllLocations(): Flow<List<LocationInfoEntity>>
}