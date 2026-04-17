package com.example.appgimnasio.Views.UserViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.ViewModels.GimnasioViewModel

import com.example.appgimnasio.ViewModels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministracionView(navController: NavController,userVM: UserViewModel,gimnasioVM: GimnasioViewModel){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = {
            Text("ADMINISTRACIÓN", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
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
        bottomBar = {
            BottonNavigation("Perfil",navController)
        }

        ) {
        //Contenido
        AdministracionContent(navController,it,userVM,gimnasioVM)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministracionContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel,gimnasioVM: GimnasioViewModel){
    //Variables para manejar la informacion y manejar el estado de la UI

    var arrayPorcentajeUsuarios = userVM.porcentajeUsuariosapp
    val arrayGimnasio = userVM.porcentajeGimnasiosapp




    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn() {

            item {
                MovimientosTabs(arrayPorcentajeUsuarios,arrayGimnasio,gimnasioVM,navController)
            }

        }

    }
}