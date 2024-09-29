package com.richaa2.mappdp.presentation.locationDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.LatLng
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.presentation.locationDetails.components.ConfirmationDialog
import com.richaa2.mappdp.utils.base64ToBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    locationDetailsViewModel: LocationDetailsViewModel = hiltViewModel(),
    locationId: Long,
    onBack: () -> Unit,
    onEdit: (LatLng, Long) -> Unit,
) {
    val uiState by locationDetailsViewModel.uiState.collectAsState()
    val errorState by locationDetailsViewModel.errorState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    //TODO GLOBAL SNACKBAR AND ERROR HANDLING
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = locationId) {
        locationDetailsViewModel.loadLocationDetails(locationId)
    }

    LaunchedEffect(errorState) {
        errorState?.let {
            snackbarHostState.showSnackbar(it)
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
                    uiState.let {
                        if (uiState is LocationDetailsViewModel.LocationDetailsState.Success) {
                            IconButton(onClick = {
                                showDeleteDialog = true
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Location")
                            }
                        }
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
            uiState.let {
                if (it is LocationDetailsViewModel.LocationDetailsState.Success) {
                    FloatingActionButton(
                        onClick = {
                            onEdit(
                                LatLng(
                                    it.location.latitude,
                                    it.location.longitude
                                ), locationId
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Location")
                    }
                }
            }

        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }

    ) { innerPadding ->

        when (val state = uiState) {
            is LocationDetailsViewModel.LocationDetailsState.Success -> {
                LocationDetailContent(
                    modifier = Modifier.padding(innerPadding),
                    location = state.location
                )
            }

            is LocationDetailsViewModel.LocationDetailsState.Loading -> {
                LoadingScreen(innerPadding)
            }
        }
        if (showDeleteDialog) {
            ConfirmationDialog(
                title = "Confirm Delete",
                message = "Are you sure you want to delete this location?",
                onConfirm = {
                    locationDetailsViewModel.deleteLocation(locationId)
                    onBack()
                    showDeleteDialog = false
                },
                onDismiss = { showDeleteDialog = false }
            )
        }
    }

}

@Composable
private fun LoadingScreen(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun LocationDetailContent(
    modifier: Modifier = Modifier,
    location: LocationInfo,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            if (location.imageUrl?.base64ToBitmap() != null) {
                Image(
                    painter = rememberAsyncImagePainter(location.imageUrl.base64ToBitmap()),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.small)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            shape = MaterialTheme.shapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image for this location",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = location.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = location.description ?: "Description empty",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}

@Preview
@Composable
private fun LocationDetailsPreview() {
    MapPDPTheme {
        MapPDPTheme {
            LocationDetailContent(
                location = LocationInfo(
                    id = 1,
                    title = "Lviv",
                    description = "Львів (МФА: [ˈʎʋiu̯] ( прослухати)) — місто в Україні, адміністративний центр області, агломерації, району, міської громади, національно-культурний та освітньо-науковий осередок країни, великий промисловий центр і транспортний вузол, вважається столицею Галичини та центром Західної України. За кількістю населення — п'яте місто країни (717 273 станом на 01.01.2022)[2].\n" +
                            "\n" +
                            "Львів заснував король Данило приблизно у 1231—1235 роках (перша згадка від 1256 року). Близько 1272 року місто стало столицею Королівства Русі (Галицько-Волинського князівства). Невдовзі після смерті князя Юрія II Львів на понад чотири століття опинився під владою Королівства Польського. 1356 року місто отримало маґдебурзьке право; в добу Середньовіччя Львів був важливим торговельним центром. За австрійського панування місто стало осередком українського та польського національного рухів. Після розпаду Австро-Угорщини восени 1918 року Львів деякий час був столицею Західноукраїнської Народної Республіки, але після українсько-польської битви за місто в листопаді 1918 перейшов до Польщі, що в 1922—1923 було визнано міжнародними пактами та угодами. Під час Другої світової війни місто захопив Радянський Союз, а згодом — Німеччина. Після війни було юридично закріплено ялтинську угоду 1945, за якою Східна Галичина і зокрема Львів лишався у складі Української РСР. 1946 року між Польщею та УРСР відбувся обмін населення, який разом із наслідками війни суттєво вплинув на населення Львова. З 1991 року Львів перебуває у складі України.\n" +
                            "\n",
                    latitude = 12.0,
                    longitude = 12.0,
                    imageUrl = null,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }
}