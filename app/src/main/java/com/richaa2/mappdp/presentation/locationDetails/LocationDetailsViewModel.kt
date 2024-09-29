package com.richaa2.mappdp.presentation.locationDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.GetLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val deleteLocationInfoUseCase: DeleteLocationInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LocationDetailsState>(LocationDetailsState.Loading)
    val uiState: StateFlow<LocationDetailsState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    fun loadLocationDetails(locationId: Long) {
        viewModelScope.launch {
            try {
                val location = getLocationInfoUseCase(locationId)
                location?.let {
                    _uiState.value = LocationDetailsState.Success(it)
                } ?: run {
                    _errorState.value = "Location not found"
                }
            } catch (e: Exception) {
                _errorState.value = e.localizedMessage
            }
        }
    }

    fun deleteLocation(locationId: Long) {
        viewModelScope.launch {
            deleteLocationInfoUseCase(locationId)
        }
    }

    sealed class LocationDetailsState {
        data object Loading : LocationDetailsState()
        data class Success(val location: LocationInfo) : LocationDetailsState()
    }
}