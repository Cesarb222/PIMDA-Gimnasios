package com.example.appgimnasio.Model

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.EstadisticaGim
import com.example.appgimnasio.Componets.EstadisticaUsuarios
import com.example.appgimnasio.Componets.UpdateGym
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.GimnasioViewModel
import com.example.appgimnasio.ViewModels.UserViewModel

sealed class Items_movimientos (
    var titulo: String,
    var iconSelected: ImageVector,
    var iconUnSelected: ImageVector,
    var screen: @Composable (Array<Double>, Array<Double>, GimnasioViewModel, NavController)-> Unit
){
    @SuppressLint("ViewModelConstructorInComposable")
    object tab_usuarios: Items_movimientos(
        "% Usuarios",
        Icons.Filled.AppRegistration,
        Icons.Filled.AppRegistration,
        {array,array2,gimnasio,navController-> EstadisticaUsuarios(array) }
    )
    object tab_gimnasio: Items_movimientos(
        "% Gimnasio",
        Icons.Filled.Assessment,
        Icons.Filled.Assessment,
        {array,array2,gimnasio ,navController-> EstadisticaGim(array2) }
    )
    object tab_update: Items_movimientos(
        "Actualizar Gym",
        Icons.Filled.Edit,
        Icons.Filled.Edit,
        { array,array2,gimnasio,navController ->UpdateGym(gimnasio,navController) }
    )
}