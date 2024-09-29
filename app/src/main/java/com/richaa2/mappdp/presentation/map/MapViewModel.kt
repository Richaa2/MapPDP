package com.richaa2.mappdp.presentation.map

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.richaa2.mappdp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsInfoUseCase
import com.richaa2.mappdp.domain.usecase.SaveLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getSavedLocationsInfoUseCase: GetSavedLocationsInfoUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    private val _savedLocations = MutableStateFlow<List<com.richaa2.mappdp.domain.model.LocationInfo>>(emptyList())
    val savedLocations: StateFlow<List<com.richaa2.mappdp.domain.model.LocationInfo>> = _savedLocations

    init {
        loadSavedLocations()
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                _currentLocation.value = location
            }
    }

    private fun loadSavedLocations() {
        viewModelScope.launch {
            getSavedLocationsInfoUseCase.invoke().collect { locations ->
                _savedLocations.value = locations
            }
        }
    }
}