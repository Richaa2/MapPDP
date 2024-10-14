package com.richaa2.mappdp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.richaa2.mappdp.navigation.NavArgs.LATITUDE
import com.richaa2.mappdp.navigation.NavArgs.LOCATION_ID
import com.richaa2.mappdp.navigation.NavArgs.LONGITUDE
import com.richaa2.mappdp.presentation.addLocation.AddLocationScreen
import com.richaa2.mappdp.presentation.locationDetails.LocationDetailsScreen
import com.richaa2.mappdp.presentation.map.MapScreen
import com.richaa2.mappdp.presentation.map.MapViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.Map.route) {
        composable(route = NavRoutes.Map.route) {
            val mapViewModel: MapViewModel = hiltViewModel()
            MapScreen(
                viewModel = mapViewModel,
                onAddLocation = { latLng ->
                    navController.navigate(
                        NavRoutes.AddLocation.createRouteCreate(
                            latLng.latitude.toFloat(),
                            latLng.longitude.toFloat(),
                        )
                    )
                },
                onLocationDetails = { location ->
                    navController.navigate(NavRoutes.LocationDetails.createRoute(location.id))
                }
            )
        }
        composable(
            route = NavRoutes.AddLocation.route,
            arguments = listOf(
                navArgument(LATITUDE) { type = NavType.FloatType },
                navArgument(LONGITUDE) { type = NavType.FloatType },
                navArgument(LOCATION_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getFloat(LATITUDE)?.toDouble() ?: 0.0
            val longitude = backStackEntry.arguments?.getFloat(LONGITUDE)?.toDouble() ?: 0.0
            val locationId = backStackEntry.arguments?.getString(LOCATION_ID)?.toLongOrNull()
            AddLocationScreen(
                latitude = latitude,
                longitude = longitude,
                locationId = locationId,
                onBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                }
            )
        }
        composable(
            route = NavRoutes.LocationDetails.route,
            arguments = listOf(
                navArgument(LOCATION_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val stackLocationId = backStackEntry.arguments?.getLong(LOCATION_ID) ?: 0
            LocationDetailsScreen(
                locationId = stackLocationId,
                onBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                },
                onEdit = { latLng, locationId ->
                    navController.navigate(
                        NavRoutes.AddLocation.createRouteEdit(
                            latLng.latitude.toFloat(),
                            latLng.longitude.toFloat(),
                            locationId
                        )
                    )
                }
            )
        }
    }
}