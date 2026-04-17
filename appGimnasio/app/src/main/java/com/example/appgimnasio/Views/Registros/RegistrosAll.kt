package com.example.appgimnasio.Views.Registros

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.ViewModels.EjercicioViewModels

import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appgimnasio.Componets.CardRegistros

import com.example.appgimnasio.ViewModels.RegistroViemModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllRegistrosView(navController: NavController,userVM: UserViewModel,ejercicioVM: EjercicioViewModels,registroVM: RegistroViemModel){
    Scaffold(topBar = {
        //barra superior
        TopAppBar(title = { Text("AÑADIR REGISTRO", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
            BottonNavigation("Publicaciones",navController)
        }
    )  {
        //Si el usuario esta registrado pintamos la UI
        if (USUARIO!=null) {
            AllRegistrosContent(navController, it, userVM,ejercicioVM,registroVM)
        }else{
            navController.navigate("login")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllRegistrosContent(navController: NavController,paddingValues: PaddingValues,
                         userVM: UserViewModel,ejercicioVM: EjercicioViewModels,
                         registroVM: RegistroViemModel
) {
    registroVM.registros()
    val registros = registroVM.registrosPublicos.collectAsState().value
    LazyColumn(modifier = Modifier.padding(paddingValues)){

        item {
            Text("REGISTROS: ",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
                modifier = Modifier.padding(10.dp).fillMaxWidth())
        }
        items(registros){ item->
            if (item!=null){
                CardRegistros(item,navController)
                Spacer(modifier = Modifier.height(16.dp))
            }


        }

    }
}