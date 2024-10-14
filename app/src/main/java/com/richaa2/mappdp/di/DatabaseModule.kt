package com.richaa2.mappdp.di

import android.content.Context
import androidx.room.Room
import com.richaa2.mappdp.data.source.local.LocationDao
import com.richaa2.mappdp.data.source.local.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(
        @ApplicationContext context: Context
    ): LocationDatabase {
        return Room.databaseBuilder(
            context,
            LocationDatabase::class.java,
            "location_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: LocationDatabase): LocationDao {
        return database.locationDao()
    }
}