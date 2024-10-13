package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.common.Resource
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
) {
    operator fun invoke(locationId: Long): Flow<Resource<LocationInfo?>> {
        return locationRepository.getLocationInfoById(locationId)
    }
}