package com.example.maping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.maping.screens.* import com.example.maping.ui.theme.MapIngTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapIngTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Estado simple para controlar qué pantalla mostramos
                    var isLoggedIn by remember { mutableStateOf(false) }

                    if (isLoggedIn) {
                        // Si ya entró, mostramos el Mapa
                        MainMapScreen()
                    } else {
                        // Si no, mostramos el Login
                        LoginScreen(
                            onLoginSuccess = {
                                // Esta función se ejecuta cuando Google dice "OK"
                                isLoggedIn = true
                            }
                        )
                    }
                }
            }
        }
    }
}