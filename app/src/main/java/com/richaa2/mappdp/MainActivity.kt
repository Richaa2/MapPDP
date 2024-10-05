package com.richaa2.mappdp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.richaa2.mappdp.core.ui.theme.MapPDPTheme
import com.richaa2.mappdp.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //TODO SINGLE SCAFFOLD
    //TODO ADD UNIT TESTS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapPDPTheme {
                AppNavigation()
            }
        }
    }
}

