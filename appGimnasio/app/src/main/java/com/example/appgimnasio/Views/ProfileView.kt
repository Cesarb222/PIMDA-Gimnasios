package com.example.appgimnasio.Views

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilView(navController: NavController,userVM: UserViewModel){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = { Text("PERFIL", color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold )},
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
        //menu navegacion
        bottomBar = {
            BottonNavigation("Perfil",navController)
        }
    )  {
        //Si existe un usuario pintamos la vista si no le mandamos a login
        if (USUARIO!=null) {
            PerfilViewContent(navController, it, userVM)
        }else{
            navController.navigate("login")
        }

    }
}

@Composable
fun PerfilViewContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel){
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), verticalArrangement = Arrangement.Center){
        LazyColumn(modifier = Modifier ) {
            item {
                Row(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(Color(0xFF0F4C5C))
                        .padding(top = 10.dp, bottom = 10.dp)
                        , verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(painter = painterResource(id = R.drawable.user_svgrepo_com__2_),
                            contentDescription = "Acceso",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(start= 10.dp)
                        )
                        Spacer( modifier = Modifier.width(15.dp))
                        Text(USUARIO!!.nombre.toUpperCase(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )

                    }
                }
                Row(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(Color(0xFF0F4C5C))
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clickable {
                            userVM.logout()
                        }, verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.logout_svgrepo_com),
                            contentDescription = "Acceso",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(start = 10.dp)

                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            "CERRAR SESIÓN",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )


                    }
                }
                if (USUARIO!!.rol.equals("administrador")){
                    Row(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .background(Color(0xFF0F4C5C))
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable {
                                    navController.navigate("administrar")
                                }, verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.admin),
                                contentDescription = "Acceso",
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp)
                                    .padding(start = 10.dp)

                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                "ADMINISTRACIÓN",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White
                            )


                        }
                    }
                }
                Row(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(Color(0xFF0F4C5C))
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clickable{
                            navController.navigate("MisRegistros")
                        }
                        , verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(painter = painterResource(id = R.drawable.gymnast_with_a_dumbbell_and_heart_shape_with_lifeline_svgrepo_com),
                            contentDescription = "Acceso",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(start= 10.dp)

                        )
                        Spacer( modifier = Modifier.width(15.dp))
                        Text("VER MIS REGISTROS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )


                    }
                }

                Row(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(Color(0xFF0F4C5C))
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clickable{
                            navController.navigate("MisEntrenos")
                        }
                        , verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(painter = painterResource(id = R.drawable.dumbbells_svgrepo_com),
                            contentDescription = "Acceso",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(start= 10.dp)

                        )
                        Spacer( modifier = Modifier.width(15.dp))
                        Text("VER MIS ENTRENOS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )


                    }
                }

                Row(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier
                        .clickable{
                            if (USUARIO!!.gimnasio!=null){
                                navController.navigate("Acceso")
                            }else{
                                navController.navigate("comprarSubscripcion")
                            }
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(Color(0xFF0F4C5C))
                        .padding(top = 10.dp, bottom = 10.dp)
                        , verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(painter = painterResource(id = R.drawable.pay_code_two_svgrepo_com),
                            contentDescription = "Acceso",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(start= 10.dp)
                        )
                        Spacer( modifier = Modifier.width(15.dp))
                        Text("SUBSCRIPCIÓN",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )


                    }
                }
                Row(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(Color(0xFF0F4C5C))
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clickable{
                            navController.navigate("ejercicios")
                        }
                        , verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(painter = painterResource(id = R.drawable.gym_svgrepo_com),
                            contentDescription = "Acceso",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(start= 10.dp)

                        )
                        Spacer( modifier = Modifier.width(15.dp))
                        Text("VER EJERCICIOS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )


                    }
                }


                }
            }
    }

}