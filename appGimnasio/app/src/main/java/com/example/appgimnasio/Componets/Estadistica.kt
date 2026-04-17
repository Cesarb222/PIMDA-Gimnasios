package com.example.appgimnasio.Componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Model.GimnasioModel
import com.example.appgimnasio.ViewModels.GimnasioViewModel
import com.example.appgimnasio.R
import kotlinx.coroutines.launch

@Composable
fun EstadisticaUsuarios(array: Array<Double>){
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("Porcentaje de usuarios que utilizan la app",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF55758A),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))
        CardStats("Prueba",array)
    }
}

@Composable
fun EstadisticaGim(array: Array<Double>){
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("Porcentaje de Subscripciones que utilizan la app",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF55758A),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))
        CardStats("Prueba",array)
    }
}

@Composable
fun UpdateGym(gimnasioVM: GimnasioViewModel,navController: NavController){
    val gimnasio = gimnasioVM.gimnasio.collectAsState().value
    var tlf by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var ciudad by rememberSaveable { mutableStateOf("") }
    var horario by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }

    var alerta by rememberSaveable { mutableStateOf(false) }
    var check by rememberSaveable { mutableStateOf(false) }
    var titulo by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Actualizar gimnasio",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF55758A),
            modifier = Modifier
                .fillMaxWidth()
        )

        if (gimnasio!=null){
            nombre = ImputText2("Nombre",R.drawable.user_svgrepo_com__2_,gimnasio.nombre)
            tlf = ImputText2("Telefono",R.drawable.phone_svgrepo_com,gimnasio.telefono)
            direccion = ImputText2("Direccion",R.drawable.title_svgrepo_com,gimnasio.direccion)
            ciudad = ImputText2("Ciudad",R.drawable.ciudad,gimnasio.ciudad)
            horario = ImputText2("Horario",R.drawable.clock_plus_svgrepo_com,gimnasio.horario)


            val scope = rememberCoroutineScope()
            ElevatedButton(
                enabled =
                if (!nombre.equals(gimnasio.nombre) || !tlf.equals(gimnasio.telefono)
                    || !direccion.equals(gimnasio.direccion) || !ciudad.equals(gimnasio.ciudad)
                    || !horario.equals(gimnasio.horario)) true else false,
                onClick = {
                    if (!horario.matches("^(?:[01]\\d|2[0-5]):[0-5]\\d-(?:[01]\\d|2[0-5]):[0-5]\\d\$".toRegex())) {
                        titulo = "Error"
                        mensaje = "Formato de horario no válido debe ser: 'HH:MM-HH:MM' "
                    }else{
                        gimnasioVM.updateGimnasio(
                            gimnasio.copy(nombre = nombre,
                                telefono = tlf,
                                direccion = direccion,
                                ciudad = ciudad,
                                horario = horario)
                        )
                        titulo = "Exito"
                        mensaje = "Gimnasio actualizado con exito"
                        check = true
                    }

                    alerta = true
                },

                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color(0xFF3F6275),
                    containerColor = Color(0xFF0F4C5C),
                    contentColor = Color.White
                )
            ) {
                Text(
                    "ACTUALIZAR GIMNASIO",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }

            if (alerta) {
                AlertMostrar(

                    titulo,
                    mensaje,
                    confirmText = "ACEPTAR",
                    onConfirmClick = {
                        alerta = false
                        if (check) navController.navigate("Home")
                    }, valor = true,
                    onDismissClick = {},
                    imagen = if (check) R.raw.tick else null,
                    modifier = Modifier.padding(start = 40.dp)
                )
            }
        }


    }
}

