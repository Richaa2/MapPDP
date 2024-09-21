package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(locationId: Long) = locationRepository.getLocationInfoById(locationId)
}