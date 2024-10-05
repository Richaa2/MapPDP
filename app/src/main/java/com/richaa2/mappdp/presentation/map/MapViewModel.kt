package com.richaa2.mappdp.presentation.map

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsInfoUseCase
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

    private val _savedLocations =
        MutableStateFlow<List<LocationInfo>>(emptyList())
    val savedLocations: StateFlow<List<LocationInfo>> =
        _savedLocations

    init {
        loadSavedLocations()
//        loadMockData()
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
//TODO REMOVE LATER
    private fun loadMockData() {
        viewModelScope.launch {
            val lviv = LatLng(49.842957, 24.031111)
            val locations = mutableListOf<LocationInfo>()
            for (i in 0..10) {
                val location = LocationInfo.generateRandomLocationAround(lviv)
                locations.add(location)
            }
            _savedLocations.value = locations
        }
    }
}