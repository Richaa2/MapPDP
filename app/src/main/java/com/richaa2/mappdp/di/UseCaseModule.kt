package com.richaa2.mappdp.di

import com.richaa2.mappdp.domain.repository.LocationRepository
import com.richaa2.mappdp.domain.usecase.DeleteLocationUseCase
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsUseCase
import com.richaa2.mappdp.domain.usecase.SaveLocationUseCase
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
    ): SaveLocationUseCase {
        return SaveLocationUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedLocationsUseCase(
        locationRepository: LocationRepository
    ): GetSavedLocationsUseCase {
        return GetSavedLocationsUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteLocationUseCase(
        locationRepository: LocationRepository
    ): DeleteLocationUseCase {
        return DeleteLocationUseCase(locationRepository)
    }
}