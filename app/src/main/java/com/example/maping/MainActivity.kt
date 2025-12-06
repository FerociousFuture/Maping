package com.example.maping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maping.screens.*
import com.example.maping.ui.theme.MapIngTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapIngTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = AppScreen.Login.route) {

                        // 1. LOGIN
                        composable(AppScreen.Login.route) {
                            LoginScreen(
                                onLoginSuccess = {
                                    // Al entrar, vamos al mapa y borramos el login del historial
                                    navController.navigate(AppScreen.Map.route) {
                                        popUpTo(AppScreen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // 2. MAPA PRINCIPAL
                        composable(AppScreen.Map.route) {
                            MainMapScreen(
                                onNavigateToUpload = { navController.navigate(AppScreen.Upload.route) },
                                onNavigateToProfile = { navController.navigate(AppScreen.Profile.route) }
                            )
                        }

                        // 3. SUBIR PUBLICACIÓN
                        composable(AppScreen.Upload.route) {
                            UploadPostScreen(
                                onPostUploaded = { navController.popBackStack() } // Vuelve atrás
                            )
                        }

                        // 4. PERFIL
                        composable(AppScreen.Profile.route) {
                            ProfileScreen(
                                onLogout = {
                                    // Al salir, volvemos al Login
                                    navController.navigate(AppScreen.Login.route) {
                                        popUpTo(AppScreen.Map.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}