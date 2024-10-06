package com.richaa2.mappdp.presentation.addLocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.richaa2.mappdp.presentation.addLocation.components.ImagePicker
import com.richaa2.mappdp.presentation.addLocation.utils.MAX_TITLE_LENGTH
import com.richaa2.mappdp.utils.byteArrayToBitmap
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: AddLocationViewModel = hiltViewModel(),
    latitude: Double,
    longitude: Double,
    locationId: Long?,
    onBack: () -> Unit,
    ) {
    val selectedImageByteArray by viewModel.selectedImageState.collectAsState()
    val title by viewModel.titleState.collectAsState()
    val description by viewModel.descriptionState.collectAsState()
    val errorMessage by viewModel.errorState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initLocationInfo(locationId)
    }

    LaunchedEffect(Unit) {
        viewModel.onNavigateBackAction.collectLatest { shouldNavigateBack ->
            if (shouldNavigateBack) {
                onBack()
            }
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (locationId == null) "Add Location" else "Edit Location") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
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
                modifier = Modifier,
                onClick = { viewModel.saveLocation(latitude, longitude) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Save, contentDescription = "Save Location")
            }
        },
    ) { innerPadding ->
        AddLocationContent(
            modifier = Modifier
                .padding(innerPadding),
            selectedImageByteArray = selectedImageByteArray,
            title = title,
            description = description,
            errorMessage = errorMessage,
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
    selectedImageByteArray: ByteArray?,
    title: String,
    description: String?,
    errorMessage: String?,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageSelected: (ByteArray?) -> Unit,
    onRemoveSelectedImage: () -> Unit,
) {
    val bitmap = remember(selectedImageByteArray) {
        selectedImageByteArray?.byteArrayToBitmap()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagePicker(
                selectedImageBitmap = bitmap,
                onImageSelected = onImageSelected,
                onImageRemoved = onRemoveSelectedImage
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = {
                    if (it.length <= MAX_TITLE_LENGTH) onTitleChange(it)
                },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description ?: "",
                onValueChange = onDescriptionChange,
                label = { Text("Description (Optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(16.dp))
            errorMessage?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddLocationContentPreview() {
    MapPDPTheme {
        AddLocationContent(
            selectedImageByteArray = null,
            title = "",
            description = "",
            errorMessage = null,
            onTitleChange = {},
            onDescriptionChange = {},
            onImageSelected = {},
            onRemoveSelectedImage = {}
        )
    }
}
