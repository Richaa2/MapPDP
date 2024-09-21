package com.richaa2.mappdp.presentation.locationDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.GetLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.UpdateLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val updateLocationInfoUseCase: UpdateLocationInfoUseCase,
    private val deleteLocationInfoUseCase: DeleteLocationInfoUseCase,

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationDetailsUiState())
    val uiState: StateFlow<LocationDetailsUiState> = _uiState.asStateFlow()

    fun loadLocationDetails(locationId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val location = getLocationInfoUseCase(locationId)
                _uiState.update { it.copy(isLoading = false, location = location) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage) }
            }
        }
    }

    fun updateLocation(updatedLocation: LocationInfo) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdating = true, errorMessage = null) }
            try {
                updateLocationInfoUseCase(updatedLocation)
                _uiState.update { it.copy(isUpdating = false, location = updatedLocation) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isUpdating = false, errorMessage = e.localizedMessage) }
            }
        }
    }

    fun deleteLocation(locationId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true, errorMessage = null) }
            try {
                deleteLocationInfoUseCase(locationId)
                _uiState.update { it.copy(isDeleting = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isDeleting = false, errorMessage = e.localizedMessage) }
            }
        }
    }

    data class LocationDetailsUiState(
        val isLoading: Boolean = false,
        val location: LocationInfo? = null,
        val isUpdating: Boolean = false,
        val isDeleting: Boolean = false,
        val errorMessage: String? = null,
    )

}