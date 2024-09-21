package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.repository.LocationRepository
import javax.inject.Inject

class SaveLocationInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(locationInfo: LocationInfo) {
        locationRepository.saveLocationInfo(locationInfo)
    }
}