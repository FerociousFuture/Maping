package com.example.maping

sealed class AppScreen(val route: String) {
    object Login : AppScreen("login")
    object Map : AppScreen("map")
    object Upload : AppScreen("upload")
    object Profile : AppScreen("profile")
    object Detail : AppScreen("detail")
}