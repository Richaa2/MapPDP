package com.richaa2.mappdp.presentation.addLocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.richaa2.mappdp.presentation.addLocation.components.ImagePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: AddLocationViewModel = hiltViewModel(),
    latitude: Double,
    longitude: Double,
    onSave: () -> Unit,
    onBack: () -> Unit

) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaving) {
        if (uiState.isSaving && uiState.errorMessage == null) {
            onSave()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Location") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,

                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.saveLocation(latitude, longitude) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Save, contentDescription = "Save Location")
            }
        }
    ) { innerPadding ->
        AddLocationContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onTitleChange = { viewModel.onTitleChange(it) },
            onDescriptionChange = { viewModel.onDescriptionChange(it) },
            onImageSelected = { uri -> viewModel.onImageSelected(uri) },
            onRemoveSelectedImage = { viewModel.onRemoveSelectedImage() }
        )
    }
}

@Composable
fun AddLocationContent(
    modifier: Modifier = Modifier,
    uiState: AddLocationViewModel.AddLocationUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageSelected: (String) -> Unit,
    onRemoveSelectedImage: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ImagePicker(
            selectedImageUri = uiState.selectedImageUri,
            onImageSelected = onImageSelected,
            onImageRemoved = {
                onRemoveSelectedImage()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description (Optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(16.dp))

        uiState.errorMessage?.let { errorMsg ->
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddLocationContentPreview() {
    MapPDPTheme {
        AddLocationContent(
            uiState = AddLocationViewModel.AddLocationUiState(),
            onTitleChange = {},
            onDescriptionChange = {},
            onImageSelected = {},
            onRemoveSelectedImage = {}
        )
    }
}
