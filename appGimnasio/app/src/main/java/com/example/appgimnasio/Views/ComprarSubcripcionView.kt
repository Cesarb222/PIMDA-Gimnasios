package com.example.appgimnasio.Views

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.appgimnasio.Componets.ImputCVC
import com.example.appgimnasio.Componets.ImputCardNumber
import com.example.appgimnasio.Componets.ImputText
import com.example.appgimnasio.Componets.MonthYearPickerField
import com.example.appgimnasio.Model.SubscripcionModel
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.GimnasioViewModel
import com.example.appgimnasio.ViewModels.SubscripcionViewModel
import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprarSubscripcion(navController: NavController,userVM: UserViewModel,
                        gimnasioVM: GimnasioViewModel,subscripcionVM: SubscripcionViewModel){
    //Barra superior
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("COMPRAR SUBSCRIPCION", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White)
                }
            })
    },

        ) {
        gimnasioVM.getGimnasio()
        //Contenido
        ComprarSubscripcionContent(navController,it,userVM,gimnasioVM,subscripcionVM)

    }
}

@Composable
fun ComprarSubscripcionContent(navController: NavController,paddingValues: PaddingValues,
                               userVM: UserViewModel,gimnasioVM: GimnasioViewModel,
                               subscripcionVM: SubscripcionViewModel){
    //variables utilizadas para manejar la informacion
    var numTargeta by rememberSaveable { mutableStateOf("") }
    var cvc by rememberSaveable { mutableStateOf("") }
    var alerta by rememberSaveable { mutableStateOf(false) }
    var aviso by rememberSaveable { mutableStateOf(false) }
    var enviarHome by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var titulo by rememberSaveable { mutableStateOf("") }

    var fechaGimnasio by remember { mutableStateOf("") }

    var f_registro by rememberSaveable{ mutableStateOf(LocalDate.now()) }
    val calendar = java.util.Calendar.getInstance()
    var mesActual by remember { mutableStateOf(calendar.get(java.util.Calendar.MONTH) + 1) }
    var añoActual by remember { mutableStateOf(calendar.get(java.util.Calendar.YEAR)) }

    val gimnasio = gimnasioVM.gimnasio.collectAsState().value
    val progreso = userVM.cargaCodigo.collectAsState().value
    val codigo = userVM.codigoRecuperacion.collectAsState().value
    val procesoSub = subscripcionVM.estadoSub.collectAsState().value

    //Pintamos la UI
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {

            item {
                if (gimnasio!=null){
                    Text(
                        "COMPRAR SUBSCRIPCION PARA ${gimnasio.nombre}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        softWrap = true,
                        color = Color(0xFF0F4C5C),
                        modifier = Modifier
                            .fillMaxWidth()


                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "El precio mensual es 35€",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF55758A),
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }

                numTargeta = ImputCardNumber("Numero de targeta", R.drawable.card_svgrepo_com)

                Row {
                    MonthYearPickerField(
                        titulo = "Fecha Caducidad",
                        modifier = Modifier.fillMaxWidth(),
                        onMonthYearSelected = { fecha ->
                            fechaGimnasio = fecha
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))


                }
                cvc = ImputCVC("CVC",R.drawable.cvc_svgrepo_com)

                if (fechaGimnasio.isNotEmpty()) {

                    var partes = fechaGimnasio.split("/")
                    val mesSeleccionado = partes[0].toInt()
                    val añoSeleccionado = partes[1].toInt()
                    if (mesActual == mesSeleccionado && añoActual ==añoSeleccionado){
                        titulo = "Error Tarjeta caducada"
                        mensaje = "La targeta proporcionada esta caducada"
                        aviso = true
                    }
                }


                Spacer(modifier = Modifier.height(15.dp))

                ElevatedButton(
                    enabled = if (fechaGimnasio.isNotEmpty() && cvc.isNotEmpty()
                        && cvc.length==3 && numTargeta.isNotEmpty()
                        && numTargeta.length ==16) true else false ,
                    onClick = {
                        if (!aviso && gimnasio!=null){
                            subscripcionVM.addSubscripcion(
                                SubscripcionModel(
                                    usuario = USUARIO!!,
                                    gimnasio = gimnasio,
                                    fechaInicio = f_registro.toString(),
                                    fechaFin = f_registro.plusDays(30).toString(),
                                    estado = true)
                            )


                            userVM.updateGimnasio(gimnasio,USUARIO!!.id!!)
                            titulo = "Exito"
                            mensaje = "Subscripcion al gimnasio pagada correctamente"
                            enviarHome = true

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
                        "COMPRAR AHORA",
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
                            aviso = false
                            alerta = false
                            if (enviarHome) navController.navigate("Home")
                        }, valor = true,
                        onDismissClick = {},
                        imagen = if (userVM.usuarioLogueado != null && !aviso) R.raw.tick else null,
                        modifier = Modifier.padding(start = 40.dp , top = 10.dp)
                    )
                }
            }

        }
    }

}