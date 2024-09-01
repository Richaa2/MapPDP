package com.richaa2.mappdp.presentation.map

import androidx.lifecycle.ViewModel
import com.richaa2.mappdp.domain.usecase.DeleteLocationUseCase
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsUseCase
import com.richaa2.mappdp.domain.usecase.SaveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase,
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase
) : ViewModel() {


}