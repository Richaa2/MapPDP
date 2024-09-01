package com.richaa2.mappdp.data.mapper

import com.richaa2.mappdp.data.source.local.LocationEntity
import com.richaa2.mappdp.domain.model.Location

class LocationMapper {

    fun fromEntityToDomain(entity: LocationEntity): Location {
        return Location(
            id = entity.id,
            latitude = entity.latitude,
            longitude = entity.longitude,
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            createdAt = entity.createdAt
        )
    }

    fun fromDomainToEntity(domain: Location): LocationEntity {
        return LocationEntity(
            id = domain.id,
            latitude = domain.latitude,
            longitude = domain.longitude,
            title = domain.title,
            description = domain.description,
            imageUrl = domain.imageUrl,
            createdAt = domain.createdAt
        )
    }
}