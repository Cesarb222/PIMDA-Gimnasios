package com.example.appgimnasio.Views.Entreno


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.AlertMostrar
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.Componets.ComboBox2
import com.example.appgimnasio.Componets.ImputText
import com.example.appgimnasio.Componets.ImputTextNumber
import com.example.appgimnasio.Model.EjerciciosModel
import com.example.appgimnasio.Model.EntrenamientosModel
import com.example.appgimnasio.ViewModels.EjercicioViewModels

import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import com.example.appgimnasio.util.Util
import java.time.LocalDate
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.EntrenamientoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntrenoView(navController: NavController,ejercicioVM: EjercicioViewModels,entrenoVM: EntrenamientoViewModel){
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
        }
    )  {

        //si existe un usuario logueado cambio el estado y pintamos la ui
        if (USUARIO!=null) {
            LaunchedEffect(Unit) {
                entrenoVM.cambiarEstado(false)
            }

            AddEntrenoContent(navController, it,ejercicioVM,entrenoVM)
        }else{
            navController.navigate("login")
        }

    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntrenoContent(navController: NavController,paddingValues: PaddingValues,
                      ejercicioVM: EjercicioViewModels,entrenoVM: EntrenamientoViewModel) {

    //Variables
    val estado = entrenoVM.estado.collectAsState().value
    val listaEjercicios = remember { mutableStateListOf<EjerciciosModel>() }
    val lista = ejercicioVM.list.collectAsState().value
    val listaEj = Util().eliminarDuplicados(lista,"nombre")

    var aceptarAlerta by rememberSaveable { mutableStateOf(false) }
    var comboBoxvalue by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var duracion by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var f_creacion by rememberSaveable { mutableStateOf(LocalDate.now()) }

    var tipo by rememberSaveable { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize(),verticalArrangement = Arrangement.Center){
        item{


            Column(modifier = Modifier.padding(10.dp).fillMaxSize()
                , horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("AÑADIR EJERCICIO",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0F4C5C),
                    modifier = Modifier.fillMaxWidth()
                )

                nombre = ImputText("Nombre entreno",R.drawable.gym_svgrepo_com)
                descripcion = ImputText("Descripción",R.drawable.message_square_lines_alt_svgrepo_com)
                duracion = ImputTextNumber("Duración Minutos",R.drawable.clock_plus_svgrepo_com)

                Spacer(modifier = Modifier.height(16.dp))

                comboBoxvalue = ComboBox2(listaEj,"Selecciona el ejercicio",true)

                Spacer(modifier = Modifier.height(16.dp))
                ElevatedButton(
                    enabled = comboBoxvalue.isNotBlank(),
                    onClick = {
                        if (!listaEjercicios.contains(Util().devolverEjercicio(lista,comboBoxvalue))){
                            val ej = Util().devolverEjercicio(lista,comboBoxvalue)
                            if (ej != null) listaEjercicios.add(ej)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                    ,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFF3F6275),
                        containerColor = Color(0xFF0F4C5C) ,
                        contentColor = Color.White
                    ),
                ) {
                    Text("AÑADIR EJERCICIO",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (listaEjercicios.size>0){
                    Text("Ejercicios Seleccionados",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF0F4C5C)
                        )
                    listaEjercicios.forEach {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween){
                            Text(it.nombre,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF0F4C5C),
                                modifier = Modifier.padding(top = 8.dp))
                            ElevatedButton(
                                enabled = listaEjercicios.size>0,
                                onClick = {
                                    listaEjercicios.remove(it)
                                },
                                modifier = Modifier

                                ,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFCE0000),
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(imageVector = Icons.Default.Delete,
                                    contentDescription = "Borrar",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                ElevatedButton(
                    enabled = listaEjercicios.size>0 && nombre.isNotBlank()&& duracion.toInt()>0,
                    onClick = {

                        entrenoVM.addEntrenamiento(
                            EntrenamientosModel(
                                nombre = nombre, descripcion = descripcion,
                                duracion = duracion.toInt(), f_creacion = f_creacion.toString(),
                                usuarios = USUARIO!!, ejercicios = listaEjercicios
                            )
                        )
                        aceptarAlerta = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()

                    ,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFF3F6275),
                        containerColor = Color(0xFF0F4C5C) ,
                        contentColor = Color.White
                    )
                ) {
                    Text("AÑADIR ENTRENO",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                if (aceptarAlerta) {
                    AlertMostrar(
                        "Entreno insertado",
                        "Volviendo a mis entrenos",
                        confirmText = "ACEPTAR",
                        onConfirmClick = {
                            aceptarAlerta = false
                            if (estado) navController.navigate("MisEntrenos")
                        }, valor = true,
                        onDismissClick = {},
                        imagen = if (estado) R.raw.tick else null,
                        modifier = Modifier.padding(start = 40.dp)
                    )
                }
            }
        }
    }
}