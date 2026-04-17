package com.example.appgimnasio.Views

import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.AlertMostrar
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.R
import com.example.appgimnasio.util.Util
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Imc(navController: NavController){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = { Text("IMC", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
            BottonNavigation("imc_calculator",navController)
        }
    ) {
        //Contenido
        ImcContent(navController,it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImcContent(navController: NavController,paddingValues: PaddingValues){
    var hombreclick by rememberSaveable { mutableStateOf(true) }
    var mujerclick by rememberSaveable { mutableStateOf(true ) }
    var genero by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf(0f) }
    var peso by rememberSaveable { mutableStateOf(0f) }
    var estadoBoton by rememberSaveable { mutableStateOf(false) }
    var aceptarAlerta by rememberSaveable { mutableStateOf(false) }


    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    horizontalArrangement = Arrangement.Absolute.SpaceAround

                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = Util().seleccionarColor(hombreclick))
                            .weight(1f)
                            .clickable{
                                if (!mujerclick) mujerclick = true
                                hombreclick = !hombreclick
                                genero = "Hombre"
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id = R.drawable.men_svgrepo_com),
                            contentDescription = "Hombre",
                            modifier = Modifier)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = Util().seleccionarColor(mujerclick))
                            .weight(1f)
                            .clickable{
                                if (!hombreclick) hombreclick = true
                                mujerclick = !mujerclick
                                genero = "Mujer"
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id = R.drawable.women_svgrepo_com),
                            contentDescription = "Mujer",
                            modifier = Modifier
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0F4C5C))
                        ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                        ){
                        Text("ALTURA EN CM",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Slider(
                            value = altura,
                            onValueChange = {
                                altura = it },
                            valueRange = 0f..250f,
                            colors = SliderDefaults.colors(
                                activeTrackColor = Color.White,
                                inactiveTrackColor = Color.White.copy(alpha = 0.35f),
                                thumbColor = Color.White,
                                activeTickColor = Color.Transparent,
                                inactiveTickColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(if (altura==0f) "" else altura.toInt().toString()+" CM",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0F4C5C))
                    ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(" PESO EN KG",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )

                        Slider(
                            value = peso,
                            onValueChange = { peso = it },
                            valueRange = 0f..200f,
                            colors = SliderDefaults.colors(
                                activeTrackColor = Color.White,
                                inactiveTrackColor = Color.White.copy(alpha = 0.35f),
                                thumbColor = Color.White,
                                activeTickColor = Color.Transparent,
                                inactiveTickColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(if (peso==0f) "0 kg" else "${peso.toInt()} KG",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                if (genero.isNotBlank() && peso>0 && altura>0) estadoBoton = true
                else estadoBoton = false
                ElevatedButton(
                    enabled = estadoBoton,
                    onClick = {
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
                    Text("CALCULAR IMC",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                if (aceptarAlerta){
                    AlertMostrar(
                        "Su IMC aproximado: ${"%.2f".format(Util().calcularImc(altura.roundToInt(),peso))}"
                        ,"Valor aproximado: ${Util().resultadoImc(altura.roundToInt(),peso)} ",
                        confirmText = "ACEPTAR",
                        onConfirmClick = {
                            aceptarAlerta = false
                        }, onDismissClick = {}
                        , valor = null,
                        imagen = null,
                        modifier = null
                    )
                }


            }

        }

    }
}