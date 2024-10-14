package com.richaa2.mappdp.navigation

import com.richaa2.mappdp.navigation.AppDestinations.ADD_LOCATION_ROUTE
import com.richaa2.mappdp.navigation.AppDestinations.LOCATION_DETAILS_ROUTE
import com.richaa2.mappdp.navigation.AppDestinations.MAP_ROUTE
import com.richaa2.mappdp.navigation.AppScreen.ADD_LOCATION
import com.richaa2.mappdp.navigation.AppScreen.LOCATION_DETAILS

sealed class NavRoutes(val route: String) {
    data object Map : NavRoutes(MAP_ROUTE)
    data object AddLocation : NavRoutes(ADD_LOCATION_ROUTE) {
        fun createRouteCreate(latitude: Float, longitude: Float) =
            "$ADD_LOCATION/$latitude/$longitude/"

        fun createRouteEdit(latitude: Float, longitude: Float, locationId: Long) =
            "$ADD_LOCATION/$latitude/$longitude/${locationId}"
    }
    data object LocationDetails : NavRoutes(LOCATION_DETAILS_ROUTE) {
        fun createRoute(locationId: Long) =
            "$LOCATION_DETAILS/$locationId"
    }
}