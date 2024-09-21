package com.richaa2.mappdp.presentation.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.usecase.SaveLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val saveLocationInfoUseCase: SaveLocationInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddLocationUiState())
    val uiState: StateFlow<AddLocationUiState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    fun onImageSelected(uri: String) {
        _uiState.update { it.copy(selectedImageUri = uri) }
    }
    fun onRemoveSelectedImage() {
        _uiState.update { it.copy(selectedImageUri = null) }
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        val title = _uiState.value.title.trim()
        val description = _uiState.value.description.trim()

        if (title.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Title cannot be empty") }
            return
        }

        viewModelScope.launch {
            val locationInfo = LocationInfo(
                latitude = latitude,
                longitude = longitude,
                title = title,
                description = if (description.isEmpty()) null else description,
                imageUrl = _uiState.value.selectedImageUri,
                createdAt = System.currentTimeMillis()
            )
            try {
                saveLocationInfoUseCase(locationInfo)
                _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, errorMessage = e.localizedMessage) }
            }
        }
    }

    data class AddLocationUiState(
        val title: String = "",
        val description: String = "",
        val selectedImageUri: String? = null,
        val isSaving: Boolean = false,
        val errorMessage: String? = null
    )
}