package com.richaa2.mappdp.presentation.map.components

import android.location.Location
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch

@Composable
fun MapFloatingActionButton(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    currentLocation: Location,
) {
    val coroutineScope = rememberCoroutineScope()
    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(currentLocation.latitude, currentLocation.longitude),
                        15f
                    ),
                    durationMs = 1000
                )
            }
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.MyLocation,
            contentDescription = "Current Location"
        )
    }
}