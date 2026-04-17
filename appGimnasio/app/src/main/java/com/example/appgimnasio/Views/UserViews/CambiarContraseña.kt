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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.appgimnasio.Componets.ComboBox
import com.example.appgimnasio.Componets.DatePickerFieldToModal
import com.example.appgimnasio.Componets.ImputText
import com.example.appgimnasio.Componets.PasswdInput
import com.example.appgimnasio.R
import com.example.appgimnasio.util.Util
import com.example.appgimnasio.ViewModels.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarContraseña(navController: NavController,userVM: UserViewModel,email: String){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = {
            Text("CAMBIAR CONTRASEÑA", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),)
    },

        ) {
        //Contenido
        CambiarContraseñaContent(navController,it,userVM,email)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarContraseñaContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel,email: String){
    //Variables para manejar la informacion y manejar el estado de la UI

    var password by rememberSaveable { mutableStateOf("") }
    var rPassword by  rememberSaveable { mutableStateOf("") }


    var alerta by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var titulo by rememberSaveable { mutableStateOf("") }
    var enviarHome by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf(false) }

    val estado = userVM.estadonewPassword.collectAsState().value

    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {

            item {
                Text("RESTABLECER",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color(0xFF0F4C5C),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            navController.navigate("Registro")
                        },

                    )

                password = PasswdInput("Contraseña Nueva",R.drawable.password_svgrepo_com__1_)
                rPassword = PasswdInput("Repetir Contraseña",R.drawable.password_svgrepo_com__1_)
                Spacer(modifier = Modifier.height(10.dp))




                Spacer(modifier = Modifier.height(15.dp))



                val scope = rememberCoroutineScope()
                Spacer(modifier = Modifier.height(15.dp))
                ElevatedButton(
                    enabled = if (password.isNotBlank() && rPassword.isNotBlank()) true else false,
                    onClick = {
                        error = false


                            if (!password.equals(rPassword)) {
                                alerta = true
                                titulo = "Error"
                                mensaje = "Las contraseñas no coinciden"
                                error = true
                            }else{
                                userVM.updatePassword(email,password)
                                enviarHome = true
                                estado == true
                            }
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
                    Text("MODIFICAR CONTRASEÑA",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                if (estado == true){
                    titulo = "Exito"
                    mensaje = "Contraseña cambiada correctamente, volviendo a login"
                    alerta = true
                }
                if (alerta) {
                    AlertMostrar(

                        titulo,
                        mensaje,
                        confirmText = "ACEPTAR",
                        onConfirmClick = {
                            alerta = false
                            if (enviarHome) navController.navigate("login")
                        }, valor = true,
                        onDismissClick = {},
                        imagen = if (estado == true) R.raw.tick else null,
                        modifier = Modifier.padding(start = 40.dp)
                    )
                }
            }

        }

    }
}