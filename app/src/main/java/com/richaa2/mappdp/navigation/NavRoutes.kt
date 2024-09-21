package com.richaa2.mappdp.navigation

sealed class NavRoutes(val route: String) {
    data object Map : NavRoutes("map")
    data object AddLocation : NavRoutes("add_location/{latitude}/{longitude}") {
        fun createRoute(latitude: Float, longitude: Float) =
            "add_location/$latitude/$longitude"
    }
    data object LocationDetails : NavRoutes("location_details/{locationId}") {
        fun createRoute(locationId: Long) =
            "location_details/$locationId"
    }
}