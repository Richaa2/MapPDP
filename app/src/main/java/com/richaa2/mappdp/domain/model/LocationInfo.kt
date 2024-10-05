package com.richaa2.mappdp.domain.model

import com.google.android.gms.maps.model.LatLng
import java.util.UUID

data class LocationInfo(
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val createdAt: Long,
) {
    //TODO TEST REMOVE LATER
    companion object {
        fun generateRandomLocationAround(latLng: LatLng): LocationInfo {
            val randomLatitude = latLng.latitude + (Math.random() - 0.5) / 10
            val randomLongitude = latLng.longitude + (Math.random() - 0.5) / 10
            LatLng(randomLatitude, randomLongitude)
            val id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
            return LocationInfo(
                id = id,
                latitude = randomLatitude,
                longitude = randomLongitude,
                title = "title",
                description = null,
                imageUrl = null,
                createdAt = System.currentTimeMillis()
            )
        }
    }

}