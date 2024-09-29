package com.richaa2.mappdp.data.repository

import com.richaa2.mappdp.data.mapper.LocationMapper
import com.richaa2.mappdp.data.source.local.LocationDao
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val locationMapper: LocationMapper,
) : LocationRepository {


    override suspend fun saveLocationInfo(locationInfo: LocationInfo) {
        val entity = locationMapper.fromDomainToEntity(locationInfo)
        locationDao.insertLocation(entity)
    }

    override fun getSavedLocationsInfo(): Flow<List<LocationInfo>> {
        return locationDao.getAllLocations().map { entities ->
            entities.map { entity ->
                locationMapper.fromEntityToDomain(entity)
            }
        }
    }

    override suspend fun getLocationInfoById(id: Long): LocationInfo? {
        val entity = locationDao.getLocationById(id)
        return entity?.let { locationMapper.fromEntityToDomain(it) }
    }

    override suspend fun updateLocationInfo(locationInfo: LocationInfo) {
        val entity = locationMapper.fromDomainToEntity(locationInfo)
        locationDao.updateLocation(entity)
    }

    override suspend fun deleteLocationInfoById(id: Long): Boolean {
        val rowsDeleted = locationDao.deleteLocationById(id)
        return rowsDeleted > 0
    }
}