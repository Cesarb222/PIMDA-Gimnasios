package com.example.appgimnasio.Views.Entreno

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.Componets.CardPersonalizada2
import com.example.appgimnasio.Componets.IndividualEntreno
import com.example.appgimnasio.ViewModels.EntrenamientoViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrenoInfoView(navController: NavController,entrenoVM: EntrenamientoViewModel,id: Long){
    Scaffold(topBar = {
        //barra superior

        TopAppBar(title = { Text("ENTRENO", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
    },//Menu de navegacion
        bottomBar = {
            BottonNavigation("Perfil",navController)
        }
    )  {
        //si existe un usuario logueado selecciono el entreno y pintamos la ui
        if (USUARIO!=null) {
            entrenoVM.getEntreno(id)
            EntrenoInfoContent(navController, it,entrenoVM)
        }else{
            navController.navigate("login")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrenoInfoContent(navController: NavController,paddingValues: PaddingValues,
                       entrenoVM: EntrenamientoViewModel) {
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    val entreno = entrenoVM.entrenoInfo.collectAsState().value
    val context = LocalContext.current
   Column(modifier = Modifier.padding(paddingValues)){
       LazyColumn(modifier = Modifier.padding().weight(1f)){
           item{
               if (entreno!=null){
                   Column (modifier = Modifier.padding(12.dp)){

                       IndividualEntreno(entreno)
                       Spacer(modifier = Modifier.height(20.dp))
                       Text("EJERCICIOS: ",
                           textAlign = TextAlign.Center,
                           fontWeight = FontWeight.Bold,
                           fontSize = 20.sp,
                           color = Color(0xFF0F4C5C),
                           modifier = Modifier.padding(10.dp))
                       Spacer(modifier = Modifier.height(16.dp))
                       LazyRow(

                       ) {
                           for (i in entreno.ejercicios.indices){
                               val image = entreno.ejercicios[i].imagen
                               val imageResId = context.resources.getIdentifier(
                                   image.split(".")[0],
                                   "drawable",
                                   context.packageName
                               )
                               item {
                                   Spacer(modifier = Modifier.width(20.dp))
                                   CardPersonalizada2(entreno.ejercicios[i].nombre.toUpperCase(),
                                       imageResId,listColor,"ejercicio/${entreno.ejercicios[i].nombre}",navController)

                               }
                           }
                       }


                   }

               }

           }

       }

   }
}