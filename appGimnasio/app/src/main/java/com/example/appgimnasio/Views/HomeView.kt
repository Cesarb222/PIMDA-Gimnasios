package com.example.appgimnasio.Views



import android.graphics.drawable.Drawable
import android.service.controls.templates.ThumbnailTemplate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import com.example.appgimnasio.Componets.BottonNavigation
import com.example.appgimnasio.R
import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import com.example.appgimnasio.util.Util
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.BrushPainter
import com.example.appgimnasio.Componets.CardHome
import com.example.appgimnasio.Componets.CardPersonalizada
import com.example.appgimnasio.Componets.LoaderData
import kotlin.collections.listOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController,userVM: UserViewModel){
    Scaffold(topBar = {
        //Barra superior
        TopAppBar(title = {
            Text("HOME", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold ) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF0F4C5C)),)
    },
        //menu de navegacion
        bottomBar = {
            BottonNavigation("Home",navController)
        }
    ) {
        //Contenido
        HomeViewContent(navController,it,userVM)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewContent(navController: NavController,paddingValues: PaddingValues,userVM: UserViewModel){

    Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
        val listColor: List<Color> =  listOf(Color(0xFF1A677E),Color(0xFF0F4C5C))
        LazyColumn(modifier = Modifier.padding(10.dp).weight(1f)) {
            item {
                Column  (
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.horizontalGradient(listColor))
                        .padding(16.dp)
                ) {
                    Row {
                        Text(
                            text = "HOLA ${USUARIO?.nombre ?: "AMIGO"}".toUpperCase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (){
                            Text(
                                text = "El éxito no es casualidad, es trabajo duro, perseverancia y sacrificio." +
                                        "\nO'Rey Pelé\n¡Sigue con tu rutina 💪!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                CardPersonalizada("EMPIEZA YA!",R.drawable.pibe2,listColor,"Home",navController)

                Spacer(modifier = Modifier.height(16.dp))
                CardPersonalizada("SUBE TUS REGISTROS!",R.drawable.femoral,listColor,"Publicar",navController)

                Spacer(modifier = Modifier.height(16.dp))
                CardPersonalizada("VISUALIZA PUBLICACIONES!",R.drawable.abdomen,listColor,"Publicaciones",navController)

            }

        }
        //Si no hay usuario, y posteriormente el usuario tiene subscripcion le mostramos el acceso
        if (USUARIO!=null && USUARIO!!.gimnasio!=null ){
            Row(modifier = Modifier.padding(10.dp)) {
                Row(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(Color(0xFF0F4C5C))
                    .padding(top = 10.dp, bottom = 10.dp)
                    .clickable{
                        navController.navigate("Acceso")
                    }
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.qr_scan_svgrepo_com),
                        contentDescription = "Acceso",
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                    Spacer( modifier = Modifier.width(15.dp))
                    Text("ACCEDER",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )

                }
            }

        }

        Spacer( modifier = Modifier.height(15.dp))
    }
}