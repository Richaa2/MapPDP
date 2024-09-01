package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.model.Location
import com.richaa2.mappdp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<Location>> {
        return locationRepository.getSavedLocations()
    }
}