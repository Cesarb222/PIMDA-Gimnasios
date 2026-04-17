package com.example.appgimnasio.Views


import android.widget.ProgressBar
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.Componets.QrCodeScreen
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import kotlinx.coroutines.delay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessView(navController: NavController,userVM: UserViewModel){
    Scaffold(topBar = {
        //barra superior
        TopAppBar(title = { Text("ACCESO", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
            BottonNavigation("Home",navController)
        }
    )  {
        //Si el usuario existe y posteriormente tiene una subscripcion
        //pintamos el contenido y si no le llevamos a login
        if (USUARIO!=null) {
            AccessViewContent(navController, it, userVM)
        }else{
            navController.navigate("login")
        }

    }
}

@Composable
fun AccessViewContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel){
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        var segundos by rememberSaveable { mutableStateOf(0f) }
        LazyColumn(modifier = Modifier.padding(10.dp) ) {
            item {

                Row(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(Color(0xFF0F4C5C))
                    .padding(10.dp)
                    .clickable{
                        navController.navigate("Home")
                              }
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.gym_svgrepo_com),
                        contentDescription = "Acceso",
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                    Spacer( modifier = Modifier.width(15.dp))
                    Text("DISFRUTA DEL ENTRENAMIENTO!!!",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }


                Spacer( modifier = Modifier.height(30.dp))

                LaunchedEffect(Unit) {
                    while (true){
                        delay(10)
                        segundos+=0.1f
                        if(segundos >= 15f){
                            segundos = 0f
                        }
                    }
                }
                var idUnico by rememberSaveable { mutableStateOf("") }
                if (segundos == 0f){
                     idUnico =  "${USUARIO!!.id}${LocalDate.now()}${System.currentTimeMillis()}"
                }

                QrCodeScreen(idUnico)
                Spacer(modifier = Modifier.height(30.dp))
                //utilizamos el componente de carga con los segundos
                LinearProgressIndicator(
                    progress = {segundos/15f},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)

                    , strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                )

            }
        }
    }

}