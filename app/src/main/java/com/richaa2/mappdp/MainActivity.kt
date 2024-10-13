package com.richaa2.mappdp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.richaa2.mappdp.designsystem.theme.MapPDPTheme
import com.richaa2.mappdp.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //TODO ADD UNIT TESTS
    // TODO SECRET
    //TODO README
    // extract strings
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MapPDPTheme {
                AppNavigation()
            }
        }
    }
}

