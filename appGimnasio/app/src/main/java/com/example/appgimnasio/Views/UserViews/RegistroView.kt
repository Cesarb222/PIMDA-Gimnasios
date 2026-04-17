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
fun RegistroView(navController: NavController,userVM: UserViewModel){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = {
            Text("LOGIN", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),)
    },

        ) {
        //Contenido
        RegistroViewContent(navController,it,userVM)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroViewContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel){
    //Variables para manejar la informacion y manejar el estado de la UI
    var nombreUsuario by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rPassword by  rememberSaveable { mutableStateOf("") }
    var genero by rememberSaveable { mutableStateOf("") }
    var f_nacimiento by rememberSaveable { mutableStateOf("") }
    var estadoBoton by rememberSaveable { mutableStateOf(false) }
    var alerta by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var titulo by rememberSaveable { mutableStateOf("") }
    var enviarHome by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {

            item {
                Text("REGISTRO",
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
                nombreUsuario = ImputText("Usuario",R.drawable.user_svgrepo_com__2_)
                apellido = ImputText("Apellido",R.drawable.user_svgrepo_com__2_)
                email = ImputText("Correo",R.drawable.email_svgrepo_com__1_ )
                password = PasswdInput("Contraseña",R.drawable.password_svgrepo_com__1_)
                rPassword = PasswdInput("Contraseña",R.drawable.password_svgrepo_com__1_)
                Spacer(modifier = Modifier.height(10.dp))
                genero = ComboBox()


                Spacer(modifier = Modifier.height(20.dp))
                DatePickerFieldToModal(
                    titulo = "Fecha de Nacimiento",
                    descripcion = "Selecciona fecha de nacimiento",
                    onDateSelected = {
                            item-> f_nacimiento = item

                    }
                )

                Spacer(modifier = Modifier.height(15.dp))
                Text("¿Ya tienes cuenta? Pincha aqui!!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF55758A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            navController.navigate("Registro")
                        },
                    textDecoration = TextDecoration.Underline,

                    )

                if (email.isNotBlank() && password.isNotBlank() &&
                    rPassword.isNotBlank() && genero.isNotBlank()
                    && f_nacimiento.isNotBlank()
                    && nombreUsuario.isNotBlank()
                    && apellido.isNotBlank()) estadoBoton = true
                else estadoBoton = false


                val scope = rememberCoroutineScope()
                Spacer(modifier = Modifier.height(15.dp))
                ElevatedButton(
                    enabled = estadoBoton,
                    onClick = {
                        error = false
                        scope.launch {
                            if (!Util().compararFechas(f_nacimiento)) {
                                alerta = true
                                titulo = "Error"
                                mensaje = "Debes ser mayor a 16 años"
                                error = true
                            }
                            if (!password.equals(rPassword)) {
                                alerta = true
                                titulo = "Error"
                                mensaje = "Las contraseñas no coinciden"
                                error = true
                            }
                            if(userVM.comprobarCorreo(email)){
                                alerta = true
                                titulo = "Error"
                                mensaje = "Ya existe el correo insertado"
                                error = true
                            }
                            if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$".toRegex())){
                                alerta = true
                                titulo = "Error"
                                mensaje = "Formato de correo no valido"
                                error = true
                            }
                            if(!error){
                                userVM.registro(nombreUsuario,apellido,email,password,f_nacimiento,genero)
                                titulo = "Inicio de sesion exitoso"
                                mensaje = ""
                                alerta = true
                                enviarHome = true
                            }
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
                    Text("INICIAR SESIÓN",
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