package com.richaa2.mappdp.presentation.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.usecase.GetLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.SaveLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.UpdateLocationInfoUseCase
import com.richaa2.mappdp.utils.base64ToByteArray
import com.richaa2.mappdp.utils.byteArrayToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val saveLocationInfoUseCase: SaveLocationInfoUseCase,
    private val updateLocationInfoUseCase: UpdateLocationInfoUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
) : ViewModel() {

    private val _selectedImageState = MutableStateFlow<ByteArray?>(null)
    val selectedImageState: StateFlow<ByteArray?> = _selectedImageState.asStateFlow()

    private val _titleState = MutableStateFlow("")
    val titleState: StateFlow<String> = _titleState.asStateFlow()

    private val _descriptionState: MutableStateFlow<String?> = MutableStateFlow("")
    val descriptionState: StateFlow<String?> = _descriptionState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _onNavigateBackAction = MutableSharedFlow<Boolean>()
    val onNavigateBackAction: SharedFlow<Boolean> = _onNavigateBackAction.asSharedFlow()

    private var selectedLocation: LocationInfo? = null

    fun initLocationInfo(locationId: Long?) {
        viewModelScope.launch {
            locationId?.let {
                selectedLocation = getLocationInfoUseCase(locationId)
                _titleState.value = selectedLocation?.title ?: ""
                _descriptionState.value = selectedLocation?.description
                _selectedImageState.value = selectedLocation?.imageUrl?.base64ToByteArray()
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _titleState.value = newTitle
    }

    fun onDescriptionChange(newDescription: String) {
        _descriptionState.value = newDescription
    }

    fun onImageSelected(imageByteArray: ByteArray?) {
        _selectedImageState.value = imageByteArray
    }

    fun onRemoveSelectedImage() {
        _selectedImageState.value = null
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        val title = _titleState.value.trim()
        val description = _descriptionState.value?.trim()

        if (title.isEmpty()) {
            _errorState.value = "Title cannot be empty"
            return
        }

        viewModelScope.launch {
            try {
                selectedLocation?.let {
                    updateLocationInfoUseCase(
                        it.copy(
                            title = title,
                            description = description,
                            imageUrl = _selectedImageState.value?.byteArrayToBase64()
                        )
                    )
                } ?: run {
                    saveLocationInfoUseCase(
                        LocationInfo(
                            title = title,
                            description = description,
                            latitude = latitude,
                            longitude = longitude,
                            imageUrl = _selectedImageState.value?.byteArrayToBase64(),
                            createdAt = System.currentTimeMillis()
                        )
                    )
                }
                _onNavigateBackAction.emit(true)
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}
