package com.example.appgimnasio.Views.Ejercicios


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.Componets.IndividualEjercicio
import com.example.appgimnasio.ViewModels.EjercicioViewModels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjercicioInfoView(navController: NavController, ejercicioVM: EjercicioViewModels,nombre: String){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = { Text(nombre.toUpperCase(), color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White)
                }
            })
    },
        //menu de navegacion
        bottomBar = {
            BottonNavigation("Perfil",navController)
        }
    )  {
        //buscamos el ejercicio por el nombre
        ejercicioVM.getEjercicioByName(nombre)
        //cargamos el contenido
        EjercicioInfoContent(navController, it,ejercicioVM)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjercicioInfoContent(navController: NavController,paddingValues: PaddingValues,ejercicioVM: EjercicioViewModels) {
    val ejercicio = ejercicioVM.ejercicioSeleccionado.collectAsState().value

    Column(modifier = Modifier.padding(paddingValues)){
        LazyColumn(modifier = Modifier.padding(16.dp)){
            item{
                if (ejercicio!=null){
                    IndividualEjercicio(ejercicio)
                }
            }

        }
    }

}