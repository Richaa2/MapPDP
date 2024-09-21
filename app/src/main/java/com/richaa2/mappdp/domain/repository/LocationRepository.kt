package com.richaa2.mappdp.domain.repository

import com.richaa2.mappdp.domain.model.LocationInfo
import kotlinx.coroutines.flow.Flow

interface LocationRepository {


    suspend fun saveLocationInfo(locationInfo: LocationInfo)


    fun getSavedLocationsInfo(): Flow<List<LocationInfo>>


    suspend fun getLocationInfoById(id: Long): LocationInfo?


    suspend fun updateLocationInfo(locationInfo: LocationInfo)


    suspend fun deleteLocationInfoById(id: Long)
}