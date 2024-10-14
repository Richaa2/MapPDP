package com.richaa2.mappdp.di

import com.richaa2.mappdp.core.common.ErrorHandler
import com.richaa2.mappdp.data.mapper.LocationMapper
import com.richaa2.mappdp.data.repository.LocationRepositoryImpl
import com.richaa2.mappdp.data.source.local.LocationDao
import com.richaa2.mappdp.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationDao: LocationDao,
        locationMapper: LocationMapper,
        errorHandler: ErrorHandler,
    ): LocationRepository {
        return LocationRepositoryImpl(locationDao, locationMapper, errorHandler)
    }

}