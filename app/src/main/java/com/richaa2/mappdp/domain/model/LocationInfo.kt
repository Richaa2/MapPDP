package com.richaa2.mappdp.domain.model

data class LocationInfo(
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String?,
    val imageUrl: String?,
)