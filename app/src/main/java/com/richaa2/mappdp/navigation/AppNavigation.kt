package com.richaa2.mappdp.navigation

import com.richaa2.mappdp.navigation.AppScreen.ADD_LOCATION
import com.richaa2.mappdp.navigation.AppScreen.LOCATION_DETAILS
import com.richaa2.mappdp.navigation.AppScreen.MAP


object AppScreen {
    const val MAP = "map"
    const val ADD_LOCATION = "add_location"
    const val LOCATION_DETAILS = "location_details"
}

object NavArgs {
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val LOCATION_ID = "locationId"
}

object AppDestinations {
    const val MAP_ROUTE = MAP
    const val ADD_LOCATION_ROUTE = "$ADD_LOCATION/{${NavArgs.LATITUDE}}/{${NavArgs.LONGITUDE}}/{${NavArgs.LOCATION_ID}}"
    const val LOCATION_DETAILS_ROUTE = "$LOCATION_DETAILS/{${NavArgs.LOCATION_ID}}"
}
