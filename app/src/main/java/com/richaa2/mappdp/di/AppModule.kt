package com.richaa2.mappdp.di


import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.richaa2.mappdp.data.mapper.LocationMapper
import com.richaa2.mappdp.data.repository.LocationRepositoryImpl
import com.richaa2.mappdp.data.source.local.LocationDao
import com.richaa2.mappdp.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideLocationRepository(
        locationDao: LocationDao,
        locationMapper: LocationMapper,
    ): LocationRepository {
        return LocationRepositoryImpl(locationDao, locationMapper)
    }
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}