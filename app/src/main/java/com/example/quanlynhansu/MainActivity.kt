package com.example.quanlynhansu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.quanlynhansu.ui.screens.AppNavigationGraph
import com.example.quanlynhansu.ui.theme.QuanLyNhanSuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            QuanLyNhanSuTheme {
                QuanLyNhanSuApp()
            }
        }
    }

    @Composable
    fun QuanLyNhanSuApp() {
        AppNavigationGraph()
    }
}