package com.example.appgimnasio.Views.Ejercicios

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.Componets.CardPersonalizada
import com.example.appgimnasio.ViewModels.EjercicioViewModels
import com.example.appgimnasio.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjerciciosView(navController: NavController,ejercicioVM: EjercicioViewModels){
    Scaffold(topBar = {
        //barra superior
        TopAppBar(title = { Text("VER EJERCICIOS", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
        //cargamos la lista de ejercicios
        ejercicioVM.cargarLista()
        //Pintamos la ui
        EjerciciosContent(navController, it,ejercicioVM)



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjerciciosContent(navController: NavController,paddingValues: PaddingValues,ejercicioVM: EjercicioViewModels) {
    //Lista para el gradirente
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    //Pillamos el contexto de la applicacion para cargar la imagen de manera dinamica
    //debido a que esta en nuestra carpeta se guarda como un entero
    val context = LocalContext.current
    val listaEjercicio = ejercicioVM.list.collectAsState().value

    Column(modifier = Modifier.padding(paddingValues)){
        LazyColumn(modifier = Modifier.padding(16.dp)){
            item{

            }
            items(listaEjercicio){ item->

                //Mostramos el ejercicio
                val image = item.imagen
                //busco el id con el contexto el nombre de la imagen lo separo hasta el punto
                //y dejo solo su nombre sin la extension
                //El siguiente parameteo es la carpeta
                //y le pasamos la definicion del contexto
                var imageResId = context.resources.getIdentifier(
                    image.split(".")[0],
                    "drawable",
                    context.packageName
                )
                if (imageResId<1) imageResId = R.drawable.pibe2
                Spacer(modifier = Modifier.height(20.dp))
                CardPersonalizada(item.nombre.toUpperCase(),
                    imageResId,listColor,"ejercicio/${item.nombre}",navController)

            }
        }
    }

}