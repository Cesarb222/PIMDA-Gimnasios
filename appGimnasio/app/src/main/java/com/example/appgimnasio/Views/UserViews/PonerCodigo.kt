package com.example.appgimnasio.Views.UserViews


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.appgimnasio.Componets.AlertMostrar
import com.example.appgimnasio.Componets.ImputText
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PonerCodigo(navController: NavController,userVM: UserViewModel,email: String){
    //Barra superior
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("RECUPERAR CONTRASEÑA", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White)
                }
            }) },

    ) {
        //Contenido
        PonerCodigoContent(navController,it,userVM,email)

    }
}

@Composable
fun PonerCodigoContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel,email: String){
    //variables utilizadas para manejar la informacion
    var codigo1 by rememberSaveable { mutableStateOf("") }
    var estadoBoton by rememberSaveable { mutableStateOf(false) }
    var alerta by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var titulo by rememberSaveable { mutableStateOf("") }

    val progreso = userVM.cargaCodigo.collectAsState().value
    val codigo = userVM.codigoRecuperacion.collectAsState().value


    //Pintamos la UI
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {

            item {

                Text(
                    "Codigo enviado a: ${email}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF55758A),
                    modifier = Modifier
                        .fillMaxWidth(),

                    )

                codigo1 = ImputText("Codigo", R.drawable.password_svgrepo_com__1_)


                    Text(
                        "Aviso! Te llegara al email un codigo!!",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF55758A),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textDecoration = TextDecoration.Underline,

                        )

                    Spacer(modifier = Modifier.height(15.dp))

                    ElevatedButton(
                        enabled = codigo1.isNotBlank(),
                        onClick = {
                            if (codigo == codigo1.toInt()){
                                navController.navigate("modificarPassword/${email}")
                            }else{
                                titulo = "Error"
                                mensaje = "El codigo esta fallido"
                                alerta = true
                            }

                            //
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
                            "MANDAR CODIGO",
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
                                // if (enviarHome) navController.navigate("Home")
                            }, valor = true,
                            onDismissClick = {},
                            imagen = if (userVM.usuarioLogueado != null) R.raw.tick else null,
                            modifier = Modifier.padding(start = 40.dp)
                        )
                    }
                }

        }
    }
}