package com.richaa2.mappdp.domain.repository

import com.richaa2.mappdp.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {


    suspend fun saveLocation(location: Location)


    fun getSavedLocations(): Flow<List<Location>>


    suspend fun getLocationById(id: Long): Location?


    suspend fun updateLocation(location: Location)


    suspend fun deleteLocationById(id: Long)
}