package com.example.appgimnasio.Views.Registros

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.appgimnasio.ViewModels.EjercicioViewModels

import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import androidx.compose.material.icons.filled.PrivateConnectivity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appgimnasio.Componets.AlertMostrar
import com.example.appgimnasio.Componets.ComboBox2
import com.example.appgimnasio.Componets.ImputText
import com.example.appgimnasio.Componets.ImputTextNumber
import com.example.appgimnasio.Model.RegistroModel
import com.example.appgimnasio.util.Util
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.RegistroViemModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRegistroView(navController: NavController,userVM: UserViewModel,ejercicioVM: EjercicioViewModels,registroVM: RegistroViemModel){
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
            BottonNavigation("Publicar",navController)
        }
    )  {
        //Si el usuario esta registrado pintamos la UI
        if (USUARIO!=null) {
            AddRegistroContent(navController, it, userVM,ejercicioVM,registroVM)
        }else{
            navController.navigate("login")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRegistroContent(navController: NavController,paddingValues: PaddingValues,
                       userVM: UserViewModel,ejercicioVM: EjercicioViewModels,
                       registroVM: RegistroViemModel
) {
    //Variables para manejar la informacion
    val lista = ejercicioVM.list.collectAsState().value
    val listaEj = Util().eliminarDuplicados(lista,"nombre")
    var ejercicio by rememberSaveable{ mutableStateOf("") }
    var cuerpo by rememberSaveable{ mutableStateOf("") }
    var tituloPublicacion by rememberSaveable{ mutableStateOf("") }
    var f_registro by rememberSaveable{ mutableStateOf(LocalDate.now()) }
    var marca by rememberSaveable{ mutableStateOf("") }
    var publico by rememberSaveable { mutableStateOf(false) }
    val ejercicioObject by ejercicioVM.ejercicioSeleccionado.collectAsState()
    val registro by registroVM.registroCargado.collectAsState()

    var titulo by rememberSaveable{ mutableStateOf("") }
    var estadoBoton by rememberSaveable { mutableStateOf(false) }
    var alerta by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var destino by rememberSaveable { mutableStateOf(false) }

    var insertar by rememberSaveable { mutableStateOf(false) }
    LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize(),verticalArrangement = Arrangement.Center){
        item{
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(10.dp).fillMaxWidth()
                ) {
                    Text("¿Quires hacerlo publico?",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF0F4C5C),
                        modifier = Modifier.padding(top = 15.dp)
                        )
                    Spacer(modifier = Modifier.width(16.dp))
                    Switch(
                        modifier = Modifier.scale(1.4f),
                        checked = publico,
                        onCheckedChange = {it->
                            publico = !publico
                        },
                        thumbContent = {
                            if (publico){
                                Icon(
                                    imageVector = Icons.Filled.Public,
                                    contentDescription = "Public",
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )

                            }else{
                                Icon(
                                    imageVector = Icons.Filled.PrivateConnectivity,
                                    contentDescription = "Private",
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            } }
                        , colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF0F4C5C),
                            checkedBorderColor = Color(0xFF0F4C5C),
                            checkedIconColor = Color.White,
                            checkedTrackColor = Color(0xFF1A677E)
                        )

                    )
                }
                tituloPublicacion=ImputText("Titulo",R.drawable.title_svgrepo_com)
                cuerpo=ImputText("Cuerpo",R.drawable.message_square_lines_alt_svgrepo_com)
                marca= ImputTextNumber("Marca",R.drawable.ruler_svgrepo_com)
                ejercicio = ComboBox2(listaEj,"Selecciona el ejercicio",true)

                LaunchedEffect(ejercicio) {
                    if (ejercicio.isNotBlank()) {
                        ejercicioVM.getEjercicioByName(ejercicio)
                    }
                }

                if (tituloPublicacion.isNotBlank() ){
                    estadoBoton = true
                }

                Spacer(modifier = Modifier.height(15.dp))
                ElevatedButton(
                    enabled = estadoBoton,
                    onClick = {
                        val marcaFloat = marca.toFloatOrNull() ?: 0f
                        val cuerpoBody = if (cuerpo.isNotBlank()) cuerpo else null;
                        val registro = RegistroModel(
                            titulo = tituloPublicacion,
                            cuerpo = cuerpoBody,
                            marca = marcaFloat,
                            f_registro = f_registro.toString(),
                            publico = publico,
                            ejercicio = if (ejercicio.isBlank()) null else ejercicioObject,
                            usuario = USUARIO!!
                        )
                        registroVM.addRegistro(registro)
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
                        "SUBIR REGISTRO",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }


                if (registro!=null){
                    titulo = "Subida exitosa"
                    mensaje = "Volver a mis registros"
                    destino = true
                }else{
                    titulo = "Error al insertar registro"
                    mensaje = "Intente de nuevo"
                }
                if (alerta) {
                    AlertMostrar(
                        titulo,
                        mensaje,
                        confirmText = "ACEPTAR",
                        onConfirmClick = {
                            alerta = false
                            if (destino) navController.navigate("Perfil")
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