package com.richaa2.mappdp.data.error

import android.net.http.HttpException
import com.richaa2.mappdp.core.common.ErrorHandler
import com.richaa2.mappdp.core.common.ResourceProvider
import com.richaa2.mappdp.R // Імпорт вашого R файлу
import java.io.IOException
import javax.inject.Inject

class DefaultErrorHandler @Inject constructor(
    private val resourceProvider: ResourceProvider,
) : ErrorHandler {
    override fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> resourceProvider.getString(
                R.string.error_server,
                throwable.message.toString()
            )

            is IOException -> resourceProvider.getString(R.string.error_network)
            else -> resourceProvider.getString(R.string.error_unknown, throwable.message.toString())
        }
    }
}