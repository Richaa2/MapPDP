package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.common.Resource
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationsInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Resource<List<LocationInfo>>> {
        return locationRepository.getSavedLocationsInfo()
    }
}