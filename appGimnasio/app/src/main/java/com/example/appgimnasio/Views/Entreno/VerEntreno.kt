package com.example.appgimnasio.Views.Entreno


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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.Componets.FloatButton
import com.example.appgimnasio.ViewModels.EntrenamientoViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.example.appgimnasio.Componets.CardEntrenos
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController,entrenoVM: EntrenamientoViewModel){
    Scaffold(topBar = {
        //barra superior

        TopAppBar(title = { Text("AÑADIR", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
        //Menu de navegacion
        bottomBar = {
            BottonNavigation("Perfil",navController)
        },
        //creo un boton flotante para que me mande a añadir entreno
        floatingActionButton = {
            FloatButton {
                navController.navigate("añadirEntreno")
            }
        }
    )  {
        //si existe un usuario logueado cargo sus entrenamientos y pintamos la ui
        if (USUARIO!=null) {
            entrenoVM.getEntrenamientos(USUARIO!!.id)
            AddContent(navController, it,entrenoVM)
        }else{
            navController.navigate("login")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContent(navController: NavController,paddingValues: PaddingValues,entrenoVM: EntrenamientoViewModel) {

    val listaEntrenos = entrenoVM.entrenos.collectAsState().value
    LazyColumn(modifier = Modifier.padding(paddingValues)){
        item{

        }
        items(listaEntrenos){ item->
            val delete = SwipeAction(
                icon = rememberVectorPainter(Icons.Default.Delete),
                background = Color.Red,
                onSwipe = {
                    entrenoVM.deleteEntrenamiento(item.id)
                }
            )

            SwipeableActionsBox(
                //El modifier hace que salga una animacion cuando se elimina
                modifier = Modifier.animateItem(),
                endActions = listOf(delete),
                swipeThreshold = 100.dp
            ) {
                Column(modifier = Modifier.padding(10.dp)){
                    CardEntrenos(item,navController)
                }



            }
        }
    }

}