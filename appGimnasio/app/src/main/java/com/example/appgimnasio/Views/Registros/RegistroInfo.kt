package com.example.appgimnasio.Views.Registros

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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

import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appgimnasio.Componets.IndividualRegistroInfo
import com.example.appgimnasio.Model.RegistroModel
import com.example.appgimnasio.ViewModels.RegistroViemModel
import androidx.compose.foundation.lazy.items
import com.example.appgimnasio.Componets.Comentario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoRegistroView(navController: NavController,registroVM: RegistroViemModel,id: Long){
    Scaffold(topBar = {
        //barra superior
        TopAppBar(title = { Text("INFORMACIÓN REGISTRO", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("MisRegistros")
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White)
                }
            })
    },
        //menu de navegacion
        bottomBar = {
            BottonNavigation("Publicar",navController)
        }
    )  {
        //Si el usuario esta registrado pintamos la UI cargamos el registro y los comentarios
        if (USUARIO!=null) {
            registroVM.registroById(id)
            registroVM.comentarios(id)
            InfoRegistroContent(navController, it,registroVM,registroVM.registroSeleccionado.collectAsState().value)
        }else{
            navController.navigate("login")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoRegistroContent(
    navController: NavController, paddingValues: PaddingValues,
    registroVM: RegistroViemModel, registro: RegistroModel?
) {
    registroVM.getMisRegistros(USUARIO!!.id)
    val comentarios = registroVM.comentariosRegistro.collectAsState().value

    LazyColumn(modifier = Modifier.padding(paddingValues)){
        item {
            if (registro!=null){
                IndividualRegistroInfo(registro,registroVM,navController)

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Spacer(Modifier.height(10.dp))
                    Text("Tienes ${comentarios.size} respuestas:",
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF0F4C5C))
                }
            }
        }
        items(comentarios){ item ->
            if(item!=null){
                Comentario(item)
            }
        }


    }
}