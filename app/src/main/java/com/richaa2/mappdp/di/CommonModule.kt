package com.richaa2.mappdp.di

import android.content.Context
import com.richaa2.mappdp.data.error.DefaultErrorHandler
import com.richaa2.mappdp.core.common.ErrorHandler
import com.richaa2.mappdp.core.common.ResourceProvider
import com.richaa2.mappdp.presentation.common.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideErrorHandler(
        resourceProvider: ResourceProvider,
    ): ErrorHandler {
        return DefaultErrorHandler(resourceProvider)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context,
    ): ResourceProvider {
        return ResourceProviderImpl(context)
    }
}