package com.richaa2.mappdp.data.repository

import com.richaa2.mappdp.data.mapper.LocationMapper
import com.richaa2.mappdp.data.source.local.LocationDao
import com.richaa2.mappdp.domain.model.Location
import com.richaa2.mappdp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val locationMapper: LocationMapper
) : LocationRepository {


    override suspend fun saveLocation(location: Location) {
        val entity = locationMapper.fromDomainToEntity(location)
        locationDao.insertLocation(entity)
    }

    override fun getSavedLocations(): Flow<List<Location>> {
        return locationDao.getAllLocations().map { entities ->
            entities.map { entity ->
                locationMapper.fromEntityToDomain(entity)
            }
        }
    }

    override suspend fun getLocationById(id: Long): Location? {
        val entity = locationDao.getLocationById(id)
        return entity?.let { locationMapper.fromEntityToDomain(it) }
    }

    override suspend fun updateLocation(location: Location) {
        val entity = locationMapper.fromDomainToEntity(location)
        locationDao.updateLocation(entity)
    }

    override suspend fun deleteLocationById(id: Long) {
        val entity = locationDao.getLocationById(id)
        entity?.let {
            locationDao.deleteLocation(it)
        }
    }
}