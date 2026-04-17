package com.example.appgimnasio.Componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.appgimnasio.Model.DESTINOS
import com.example.appgimnasio.Model.EjerciciosModel
import com.example.appgimnasio.Model.EntrenamientosModel
import com.example.appgimnasio.Model.RegistroModel
import com.example.appgimnasio.Model.UserRegistro
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.RegistroViemModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import com.example.appgimnasio.util.Util
import java.time.LocalDate

///Aqui podemos encontrar los componentes personalizados que se han utilizado a lo largo de este proyecto
@Composable
fun BottonNavigation(
    seleccionDestino: String,
    navController: NavController
){
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
        , containerColor = Color(0xFF0F4C5C)
        , contentColor = Color.White
    ) {
        DESTINOS.forEach { destination ->
            NavigationBarItem(
                selected = destination.equals(seleccionDestino),
                onClick = {
                    navController.navigate(destination.route)
                },
                icon = {

                    Icon(
                        imageVector = destination.icon,
                        contentDescription = "ir a ${destination.route}",
                        tint = Util().colorIcon(destination.route,seleccionDestino) ,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            )
        }
    }
}


@Composable
fun AlertMostrar(
    title: String,
    message: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    valor: Boolean?,
    imagen: Int?,
    modifier: Modifier?
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = { Text(text = title) },
        text = {
            if (valor != null && valor && imagen!=null&& modifier!=null){
                LoaderData(modifier,imagen)
            }
            Text(text = message) },
        confirmButton = {
            Button(onClick = { onConfirmClick() },
                colors = ButtonDefaults.buttonColors(Color(0xFF0F4C5C))) {
                Text(text = confirmText, fontWeight = FontWeight.Bold)
            }
        }
    )
}


@Composable
fun AlertAddComentario(
    confirmText: String,
    onConfirmClick: (String) -> Unit,
    onDismissClick: () -> Unit,
) {
    var cuerpo by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = { Text(text = "Insertar Comentario") },
        text = {
            Text(text = "Escriba su respuesta")
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = cuerpo,
                onValueChange = {cuerpo = it},
                label = { Text(text = "Mensaje", fontSize = 20.sp)},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.message_square_lines_alt_svgrepo_com),
                        contentDescription = "Icono",
                        modifier = Modifier.width(30.dp).height(30.dp),
                        tint = Color(0xFF0F4C5C)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                ,
            )
               },
        confirmButton = {
            Button(
                enabled = if (cuerpo.length>0) true else false,
                onClick = { onConfirmClick(cuerpo) },
                colors = ButtonDefaults.buttonColors(Color(0xFF0F4C5C))) {
                Text(text = confirmText, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            Button(onClick = { onDismissClick() },
                colors = ButtonDefaults.buttonColors(Color(0xFF0F4C5C))) {
                Text(text = "Cancelar", fontWeight = FontWeight.Bold)
            }
        }

    )
}

@Composable
fun ImputText(tipo: String,Icon: Int):String{
    var texto by rememberSaveable { mutableStateOf("") }
    TextField(
        value = texto,
        onValueChange = {texto = it},
        label = { Text(text = tipo, fontSize = 20.sp)},
        leadingIcon = {
            Icon(
                painter = painterResource(id = Icon),
                contentDescription = "Icono",
                modifier = Modifier.width(30.dp).height(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    )
    return texto
}

@Composable
fun ImputText2(tipo: String,Icon: Int,value: String):String{
    var texto by rememberSaveable { mutableStateOf(value) }
    TextField(
        value = texto,
        onValueChange = {texto = it},
        label = { Text(text = tipo, fontSize = 20.sp)},
        leadingIcon = {
            Icon(
                painter = painterResource(id = Icon),
                contentDescription = "Icono",
                modifier = Modifier.width(30.dp).height(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    )
    return texto
}


@Composable
fun ImputCardNumber(tipo: String,Icon: Int):String{
    var texto by rememberSaveable { mutableStateOf("") }
    TextField(
        value = texto,
        onValueChange = {numero->
            if (numero.length<=16 && numero.isDigitsOnly()){
                texto = numero
            }

        },
        label = { Text(text = tipo, fontSize = 20.sp)},
        leadingIcon = {
            Icon(
                painter = painterResource(id = Icon),
                contentDescription = "Icono",
                modifier = Modifier.width(30.dp).height(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    )
    return texto
}

@Composable
fun ImputCVC(tipo: String, iconRes: Int): String {
    var texto by rememberSaveable { mutableStateOf("") }

    TextField(
        value = texto,
        onValueChange = { nuevoTexto ->
            if (nuevoTexto.length <= 3 && nuevoTexto.isDigitsOnly()) {
                texto = nuevoTexto
            }
        },
        label = { Text(text = tipo, fontSize = 20.sp) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Icono",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    return texto
}

@Composable
fun ImputTextNumber(tipo: String,Icon: Int):String{
    var texto by rememberSaveable { mutableStateOf("") }
    TextField(
        value = texto,
        onValueChange = {
            if(it.isDigitsOnly()) texto = it
        },
        label = { Text(text = tipo, fontSize = 20.sp)},
        leadingIcon = {
            Icon(
                painter = painterResource(id = Icon),
                contentDescription = "Icono",
                modifier = Modifier.width(30.dp).height(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    )
    return texto
}




@Composable
fun PasswdInput(tipo: String,Icon: Int):String{
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = {password = it},
        label = { Text(text = tipo, fontSize = 20.sp)},
        visualTransformation = if(passwordVisible) VisualTransformation.None
                                else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = Icon),
                contentDescription = "Icono",
                modifier = Modifier.width(30.dp).height(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        },
        trailingIcon = {
            val icon = if (passwordVisible) Icons.Default.Visibility
            else Icons.Default.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = icon, contentDescription = "Mostrar/Ocultar contraseña")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    )
    return password
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
@Composable
fun ComboBox(): String{
    //Creo la lista con lo que va a tener el ComboBox
    val genero = listOf("Masculino","Femenino","Prefiero no decirlo")
    var expandido by remember { mutableStateOf(false) }
    var opcion by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
        , modifier = Modifier.padding(start = 20.dp, end = 20.dp),
    ) {
        TextField(
            value = opcion,
            onValueChange = {},
            readOnly = true, // Evita que el usuario escriba
            label = { Text(text = "Seleccione su genero", fontSize = 20.sp) },
            trailingIcon = {
                // esto es para que se mueva el icono segun este expandido o no dependiendo de la variable
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF0F4C5C),
                focusedBorderColor = Color(0xFF0F4C5C)
            ),
            modifier = Modifier.menuAnchor().fillMaxWidth()

        )

        // Con esto saco el menu desplegable
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            genero.forEach { it ->
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        opcion = it
                        expandido = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }

    return opcion

}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
@Composable
fun ComboBox2(list: List<String>, texto: String, enabled: Boolean): String{
    var expandido by remember { mutableStateOf(false) }
    var opcion by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
        , modifier = Modifier.padding(start = 20.dp, end = 20.dp),
    ) {
        TextField(
            value = opcion,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(text = texto, fontSize = 20.sp) },
            trailingIcon = {
                // esto es para que se mueva el icono segun este expandido o no dependiendo de la variable
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF0F4C5C),
                focusedBorderColor = Color(0xFF0F4C5C)
            ),

            modifier = Modifier.menuAnchor().fillMaxWidth()

        )

        // Con esto saco el menu desplegable
        ExposedDropdownMenu(
            expanded = expandido ,
            onDismissRequest = { expandido = false },
            modifier = Modifier.height(180.dp)
        ) {
            list.forEach { it ->
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        opcion = it
                        expandido = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }

    return opcion

}




@Composable
fun CardHome(imagen: Int){
    Image(
        painter = painterResource(imagen),
        contentDescription = "Imagen",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CardPersonalizada(texto: String,imagen: Int,listColor: List<Color>,ruta: String,navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable{
                if (ruta.equals("Home")){
                    if (USUARIO!=null) navController.navigate("Perfil")
                    else navController.navigate("login")
                }else{
                    navController.navigate(ruta)
                }

            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            CardHome(imagen)

            Column (
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(Brush.horizontalGradient(listColor))
                    .padding(10.dp)
            ){
                Text(
                    text = texto,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CardStats(texto: String,array: Array<Double>){
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    Row(
        modifier = Modifier.padding(5.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {

            Column (
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Brush.horizontalGradient(listColor))
                    .padding(10.dp)
            ){
                Text(
                    text = texto,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column (
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Brush.horizontalGradient(listColor))
                    .padding(10.dp)
            ){
                Text(
                    text = "HOMBRES: ${String.format("%.2f", array.get(0))} %",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "MUJERES: ${String.format("%.2f", array.get(1))} %",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Otros: ${String.format("%.2f", array.get(2))} %",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}

@Composable
fun CardPersonalizada2(texto: String,imagen: Int,listColor: List<Color>,ruta: String,navController: NavController){
    Row (
        modifier = Modifier
            .width(360.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable{
                navController.navigate(ruta)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            CardHome(imagen)

            Column (
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(Brush.horizontalGradient(listColor))
                    .padding(10.dp).fillMaxWidth()
            ){
                Text(
                    text = texto,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CardRegistros(registro: RegistroModel, navController: NavController){
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable{
                navController.navigate("registroInfo/${registro.id}")
            }
            .padding(10.dp)

    ) {

        Row (
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = registro.titulo,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = if (registro.publico) R.drawable.world_1_svgrepo_com__1_ else R.drawable.private_network_svgrepo_com),
                contentDescription = "Icono",
                modifier = Modifier.width(30.dp).height(30.dp),
                tint = Color(0xFF0F4C5C)
            )
        }

        Row (
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .padding(10.dp)
                .fillMaxWidth()
        ){

            var textoCorto by rememberSaveable { mutableStateOf("") }

            for (i in registro.cuerpo.toString()) {
                if (textoCorto.length<=15&& registro.cuerpo!=null) textoCorto += i
            }
            Text(
                text = if (registro.cuerpo != null && registro.cuerpo.isNotBlank() && registro.cuerpo.length>15){
                    textoCorto+"..."
                }else if(registro.cuerpo != null && registro.cuerpo.length<15 && registro.cuerpo.isNotBlank()){
                    registro.cuerpo
                }else "Ver más...",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF38525E),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(Brush.horizontalGradient(listColor))
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "VISUALIZAR REGISTRO",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}


@Composable
fun CardEntrenos(entreno: EntrenamientosModel, navController: NavController){
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable{
                navController.navigate("entreno/${entreno.id}")
            }
            .padding(10.dp)

    ) {

        Row (
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = entreno.nombre,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
                modifier = Modifier.weight(1f)
            )
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.clock_plus_svgrepo_com),
                    contentDescription = "Icono",
                    modifier = Modifier.width(30.dp).height(30.dp),
                    tint = Color(0xFF0F4C5C)
                )
                Text(
                    text = entreno.duracion.toString()+"min",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0F4C5C),
                )
            }
        }

        Row (
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .padding(10.dp)
                .fillMaxWidth()
        ){

            var textoCorto by rememberSaveable { mutableStateOf("") }

            for (i in entreno.descripcion.toString()) {
                if (textoCorto.length<=15&& entreno.descripcion!=null) textoCorto += i
            }
            Text(
                text = if (entreno.descripcion != null && entreno.descripcion.isNotBlank() && entreno.descripcion.length>15){
                    textoCorto+"..."
                }else if(entreno.descripcion != null && entreno.descripcion.length<15 && entreno.descripcion.isNotBlank()){
                    entreno.descripcion
                }else "Ver más...",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF38525E),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (
            modifier = Modifier
                .background(Brush.horizontalGradient(listColor))
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "VISUALIZAR ENTRENO",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(Brush.horizontalGradient(listColor))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Desliza para borrar",
                tint = Color.White,
                modifier = Modifier.size(25.dp))
            Text(
                text = "DESLIZA PARA BORRAR",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White,
            )
        }

    }
}

@Composable
fun IndividualEntreno(entreno: EntrenamientosModel){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Text(
                text = entreno.nombre,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
                modifier = Modifier.weight(1f)
            )
        }


        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ){


            Text(
                text = entreno.descripcion,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF38525E),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text("DURACIÓN:",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C))
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.clock_plus_svgrepo_com),
                    contentDescription = "Icono",
                    modifier = Modifier.width(30.dp).height(30.dp),
                    tint = Color(0xFF0F4C5C)
                )
                Text(
                    text = entreno.duracion.toString()+"min",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0F4C5C),
                )
            }
        }

    }
}

@Composable
fun IndividualEjercicio(ejercicio: EjerciciosModel){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Text(
                text = ejercicio.nombre.toUpperCase(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
                modifier = Modifier.weight(1f)
            )
        }


        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ){


            Text(
                text = ejercicio.descripcion,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF38525E),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text("GRUPO MUSCULAR:",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C)
            ,modifier = Modifier.padding(top = 8.dp)
            )
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.biceps_svgrepo_com),
                    contentDescription = "Icono",
                    modifier = Modifier.width(30.dp).height(30.dp),
                    tint = Color(0xFF0F4C5C)
                )
                Text(
                    text = ejercicio.grupo_muscular,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0F4C5C),
                    modifier = Modifier.padding(top = 6.dp, start = 6.dp)
                )
            }
        }

        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
        ){
            Text("MATERIALES: ",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C))

            Spacer(modifier = Modifier.height(16.dp))
            if (ejercicio.material.split(",").size>1){
                for(i in ejercicio.material.split(",")){
                    Text("- "+i,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF0F4C5C))
                }
            }else{
                Text("- "+ejercicio.material,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0F4C5C))
            }



        }

        val context = LocalContext.current
        val image = ejercicio.imagen
        var imageResId = context.resources.getIdentifier(
            image.split(".")[0],
            "drawable",
            context.packageName
        )
        if (imageResId<1) imageResId = R.drawable.pibe2

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardHome(imageResId)
        }
    }
}

@Composable
fun IndividualRegistroInfo(registro: RegistroModel,registroVM: RegistroViemModel,navController: NavController){
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    var alerta by rememberSaveable { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(10.dp)

    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .padding(bottom = 16.dp)
                .fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text("@"+registro.usuario.nombre,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
            )
        }

        Row(
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(registro.titulo,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
            )
        }
        Row(
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = if (registro.cuerpo!=null) registro.cuerpo else "",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(Brush.horizontalGradient(listColor))
                .fillMaxWidth()
            , horizontalArrangement = Arrangement.End
            ,
        ){
            var color by rememberSaveable { mutableStateOf(false) }

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Icono",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clickable {
                        alerta = true
                    }
                    .padding( 8.dp),
                tint = Color.White
                )

        }
    }
    var cuerpo by rememberSaveable { mutableStateOf("") }
    var agregar by rememberSaveable { mutableStateOf(false) }
    if (alerta){
        AlertAddComentario(
            confirmText = "Enviar",
            onConfirmClick = { texto ->
                cuerpo = texto
                alerta = false
                agregar = true
            },
            onDismissClick = {
                alerta = false
            }
        )
    }

    Text(cuerpo)

    if (agregar && cuerpo.isNotBlank()){
        registroVM.addComentario(
            UserRegistro(f_publicacion = LocalDate.now().toString(), mensaje = cuerpo, usuario = USUARIO, registro = registro)
        )
        navController.navigate("registroInfo/${registro.id}")
    }

}

@Composable
fun Comentario(comentario: UserRegistro){
    val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(10.dp)

    ) {

        Row (
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = "@${comentario.usuario!!.nombre} ${comentario.usuario!!.apellido}",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F4C5C),
                modifier = Modifier.weight(1f)
            )

        }

        Row (
            modifier = Modifier
                .background(Color(0xFFE0DEDE))
                .padding(10.dp)
                .fillMaxWidth()
        ){

            Text(
                text = comentario.mensaje,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF38525E),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(Brush.horizontalGradient(listColor))
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Publicado el:",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
            )
            Text(
                text = comentario.f_publicacion.split("T")[0],
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
            )
        }

    }
}

@Composable
fun FloatButton(onClick: () -> Unit){
    FloatingActionButton(
        onClick =  onClick,
        containerColor = Color(0xFF0F4C5C),
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar"
        )
    }
}



