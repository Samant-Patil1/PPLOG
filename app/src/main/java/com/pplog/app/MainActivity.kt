package com.pplog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pplog.app.ui.navigation.PPLOGNavHost
import com.pplog.app.ui.theme.PPLOGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPLOGTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PPLOGNavHost(navController = rememberNavController())
                }
            }
        }
    }
}
