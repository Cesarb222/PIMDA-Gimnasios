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
import com.example.appgimnasio.Componets.PasswdInput
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.UserViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navController: NavController,userVM: UserViewModel){
    //Barra superior
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("LOGIN", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),)
    },

    ) {
        //Contenido
        LoginViewContent(navController,it,userVM)

    }
}

@Composable
fun LoginViewContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel){
    //variables utilizadas para manejar la informacion
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var estadoBoton by rememberSaveable { mutableStateOf(false) }
    var alerta by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var titulo by rememberSaveable { mutableStateOf("") }
    var enviarHome by rememberSaveable { mutableStateOf(false) }

    //Pintamos la UI
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {

            item {
                Text(
                    "INICIO DE SESIÓN",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color(0xFF0F4C5C),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("Registro")
                        },

                    )
                email = ImputText("Correo", R.drawable.user_svgrepo_com__2_)
                password = PasswdInput("Contraseña", R.drawable.password_svgrepo_com__1_)

                Text(
                    "¿No tienes cuenta? Registrate aqui!!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF55758A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("Registro")
                        },
                    textDecoration = TextDecoration.Underline,

                    )
                Text(
                    "Recuperar contraseña",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF55758A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("recuperar")
                        },
                    textDecoration = TextDecoration.Underline,
                    )
                if (password.isNotBlank() && email.isNotBlank()) estadoBoton = true
                else estadoBoton = false
                Spacer(modifier = Modifier.height(15.dp))

                val scope = rememberCoroutineScope()
                ElevatedButton(
                    enabled = estadoBoton,
                    onClick = {
                        scope.launch {
                            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$".toRegex())) {
                                titulo = "Error"
                                mensaje = "Formato de correo no válido"
                            }else if(!userVM.comprobarCorreo(email)){
                                titulo = "Error"
                                mensaje = "Correo no registrado"
                            }
                            else {
                                val user = userVM.login(email, password)

                                if (user != null) {
                                    titulo = "Inicio de sesión exitoso"
                                    mensaje = ""
                                    enviarHome = true
                                } else  {
                                    titulo = "Error"
                                    mensaje = "Ocurrio un error durante el inicio de sesion"
                                }

                            }

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
                        "INICIAR SESIÓN",
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
                            if (enviarHome) navController.navigate("Home")
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