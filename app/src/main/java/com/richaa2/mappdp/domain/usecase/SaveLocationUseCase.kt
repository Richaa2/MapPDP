package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.model.Location
import com.richaa2.mappdp.domain.repository.LocationRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(location: Location) {
        locationRepository.saveLocation(location)
    }
}