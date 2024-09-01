package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.repository.LocationRepository
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Long) {
        locationRepository.deleteLocationById(id)
    }
}