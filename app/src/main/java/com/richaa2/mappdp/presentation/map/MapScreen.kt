package com.richaa2.mappdp.presentation.map

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.richaa2.mappdp.R
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.presentation.map.components.MapFloatingActionButton
import com.richaa2.mappdp.presentation.map.components.PermissionDeniedDialog
import java.io.InputStreamReader

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    onAddLocation: (LatLng) -> Unit,
    onLocationDetails: (LocationInfo) -> Unit,
) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()
    val mapProperties = remember {
        mutableStateOf(getMapProperties(context, isDarkTheme))
    }

    val uiSettings = remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false))
    }

    val cameraPositionState = rememberCameraPositionState()
    var showPermissionDialog by remember { mutableStateOf(false) }

    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = {
            if (it) {
                showPermissionDialog = false
                mapProperties.value = mapProperties.value.copy(isMyLocationEnabled = true)
                viewModel.getCurrentLocation()
            } else {
                showPermissionDialog = true
            }
        }
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
    }

    val currentLocation by viewModel.currentLocation.collectAsState()
    val savedLocations by viewModel.savedLocations.collectAsState()
    if (showPermissionDialog && locationPermissionState.status.shouldShowRationale) {
        PermissionDeniedDialog(
            onDismiss = { showPermissionDialog = false },
            onRequestPermission = { locationPermissionState.launchPermissionRequest() }
        )
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Map") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            currentLocation?.let {
                MapFloatingActionButton(
                    cameraPositionState = cameraPositionState,
                    currentLocation = it
                )
            }
        },
        content = { paddingValues ->
            MapContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                mapProperties = mapProperties.value,
                uiSettings = uiSettings.value,
                cameraPositionState = cameraPositionState,
                savedLocations = savedLocations,
                onMapLongClick = { latLng -> onAddLocation(latLng) },
                onMarkerClick = { location -> onLocationDetails(location) }
            )
        }
    )
}

@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    mapProperties: MapProperties,
    uiSettings: MapUiSettings,
    cameraPositionState: CameraPositionState,
    savedLocations: List<LocationInfo>,
    onMapLongClick: (LatLng) -> Unit,
    onMarkerClick: (LocationInfo) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState,
            onMapLongClick = onMapLongClick
        ) {
            savedLocations.forEach { location ->
                Marker(
                    state = MarkerState(position = LatLng(location.latitude, location.longitude)),
                    title = location.title,
                    snippet = location.description,
                    onClick = {
                        onMarkerClick(location)
                        true
                    }
                )
            }
        }
    }
}

fun getMapProperties(context: Context, isDarkTheme: Boolean): MapProperties {
    val mapStyle = if (isDarkTheme) {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
    } else {
        null
    }
    return MapProperties(isMyLocationEnabled = false, mapStyleOptions = mapStyle)

}

@Preview
@Composable
private fun MapScreenPreview() {
    MapPDPTheme {
        MapContent(
            mapProperties = MapProperties(),
            uiSettings = MapUiSettings(),
            cameraPositionState = rememberCameraPositionState(),
            savedLocations = listOf(),
            onMapLongClick = {},
            onMarkerClick = {},
        )
    }
}