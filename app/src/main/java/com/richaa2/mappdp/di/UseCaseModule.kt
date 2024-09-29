package com.richaa2.mappdp.di

import com.richaa2.mappdp.domain.repository.LocationRepository
import com.richaa2.mappdp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsInfoUseCase
import com.richaa2.mappdp.domain.usecase.SaveLocationInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSaveLocationUseCase(
        locationRepository: LocationRepository
    ): SaveLocationInfoUseCase {
        return SaveLocationInfoUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedLocationsUseCase(
        locationRepository: LocationRepository
    ): GetSavedLocationsInfoUseCase {
        return GetSavedLocationsInfoUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteLocationUseCase(
        locationRepository: LocationRepository
    ): DeleteLocationInfoUseCase {
        return DeleteLocationInfoUseCase(locationRepository)
    }
}