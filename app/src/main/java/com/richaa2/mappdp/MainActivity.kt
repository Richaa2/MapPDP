package com.richaa2.mappdp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.richaa2.mappdp.navigation.AppNavigation
import com.richaa2.mappdp.navigation.NavRoutes
import com.richaa2.mappdp.presentation.addLocation.AddLocationScreen
import com.richaa2.mappdp.presentation.locationDetails.LocationDetailsScreen
import com.richaa2.mappdp.presentation.map.MapScreen
import com.richaa2.mappdp.presentation.map.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapPDPTheme {
                AppNavigation()
            }
        }
    }
}

