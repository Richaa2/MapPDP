package com.richaa2.mappdp.presentation.locationDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.presentation.addLocation.components.ImagePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    locationDetailsViewModel: LocationDetailsViewModel = hiltViewModel(),
    locationId: Long,
    onBack:() -> Unit,
    onDelete: () -> Unit

) {
    val uiState by locationDetailsViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = locationId) {
        locationDetailsViewModel.loadLocationDetails(locationId)
    }

    LaunchedEffect(uiState.isDeleting) {
        if (!uiState.isDeleting && uiState.errorMessage == null ) {
            onDelete()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Location Details") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        uiState.location?.let { location ->
                            locationDetailsViewModel.deleteLocation(location.id)
                        }
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Location")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    uiState.location?.let { location ->
                        val updatedLocation = LocationInfo(
                            id = location.id,
                            latitude = location.latitude,
                            longitude = location.longitude,
                            title = locationDetailsViewModel.uiState.value.location?.title ?: location.title,
                            description = locationDetailsViewModel.uiState.value.location?.description ?: location.description,
                            imageUrl = locationDetailsViewModel.uiState.value.location?.imageUrl ?: location.imageUrl,
                            createdAt = location.createdAt
                        )
                        locationDetailsViewModel.updateLocation(updatedLocation)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Save, contentDescription = "Save Changes")
            }
        }
    ) { innerPadding ->
        LocationDetailContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onUpdate = { updatedLocation ->
                locationDetailsViewModel.updateLocation(updatedLocation)
            }
        )
    }

}


@Composable
fun LocationDetailContent(
    modifier: Modifier = Modifier,
    uiState: LocationDetailsViewModel.LocationDetailsUiState,
    onUpdate: (LocationInfo) -> Unit
) {
    var title by remember { mutableStateOf(uiState.location?.title ?: "") }
    var description by remember { mutableStateOf(uiState.location?.description ?: "") }
    var selectedImageUri by remember { mutableStateOf(uiState.location?.imageUrl) }

    LaunchedEffect(uiState.location) {
        title = uiState.location?.title ?: ""
        description = uiState.location?.description ?: ""
        selectedImageUri = uiState.location?.imageUrl
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
//TODO UPDATE TO DONT EDITABLE
//        ImagePicker(
//            selectedImageUri = selectedImageUri,
//            onImageSelected = { uri ->
//                selectedImageUri = uri
//            },
//            onImageRemoved = {
//                selectedImageUri = null
//            }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            label = { Text("Title") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = description,
//            onValueChange = { description = it },
//            label = { Text("Description (Optional)") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp),
//            maxLines = 5
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        uiState.errorMessage?.let { errorMsg ->
//            Text(
//                text = errorMsg,
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodyMedium
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
    }


}

@Preview
@Composable
private fun LocationDetailsPreview() {

    MapPDPTheme {
        val dummyLocation = LocationInfo(
            id = 1L,
            latitude = 49.841466,
            longitude = 24.031265,
            title = "Rynok Square",
            description = "is a central square of the city of Lviv, Ukraine",
            imageUrl = null,
            createdAt = System.currentTimeMillis()
        )

        val dummyUiState = LocationDetailsViewModel.LocationDetailsUiState(
            isLoading = false,
            location = dummyLocation,
            isUpdating = false,
            isDeleting = false,
            errorMessage = null
        )

        MapPDPTheme {
            LocationDetailContent(
                uiState = dummyUiState,
                onUpdate = {}
            )
        }
    }
}