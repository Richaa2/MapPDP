package com.richaa2.mappdp.domain.model

data class Location(
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val createdAt: Long
)