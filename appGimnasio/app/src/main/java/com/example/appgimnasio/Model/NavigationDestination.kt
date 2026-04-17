package com.example.appgimnasio.Model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appgimnasio.R

//En este archvivo sacamos el una lista con los destinos para la navegacion
data class NavigationDestination (
    val route: String,
    val icon: ImageVector
)

val DESTINOS = listOf(
    NavigationDestination(
        route = Rutas.HOME, icon = Icons.Default.Home,
    ),
    NavigationDestination(
        route = Rutas.IMC, icon = Icons.Default.Calculate,
    ),
    NavigationDestination(
        route = Rutas.PUBLICAR, icon = Icons.Default.AddCircle,
    ),
    NavigationDestination(
        route = Rutas.PUBLICACIONES, icon = Icons.Default.Image,
    ),
    NavigationDestination(
        route = Rutas.PERFIL, icon = Icons.Default.AccountCircle,
    ),
)

object Rutas{
    const val HOME = "Home"
    const val IMC = "imc_calculator"
    const val PUBLICAR = "Publicar"
    const val PUBLICACIONES = "Publicaciones"
    const val PERFIL = "Perfil"
}