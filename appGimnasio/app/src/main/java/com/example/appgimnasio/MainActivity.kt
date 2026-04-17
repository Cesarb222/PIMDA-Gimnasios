package com.example.appgimnasio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.appgimnasio.Navigation.NavManager
import com.example.appgimnasio.ViewModels.EjercicioViewModels
import com.example.appgimnasio.ViewModels.EntrenamientoViewModel
import com.example.appgimnasio.ViewModels.GimnasioViewModel
import com.example.appgimnasio.ViewModels.RegistroViemModel
import com.example.appgimnasio.ViewModels.SubscripcionViewModel
//import com.example.appgimnasio.ViewModels.RegistroViemModel
import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.ui.theme.AppGimnasioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userVM : UserViewModel by viewModels()
        val registroVM: RegistroViemModel by viewModels()
        val ejercicioVM: EjercicioViewModels by viewModels()
        val entrenamientoVM: EntrenamientoViewModel by viewModels()
        val gimnasioVM: GimnasioViewModel by viewModels()
        val subscripcionVM: SubscripcionViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            AppGimnasioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    //NavManager(userVM,registroVM,ejercicioVM);
                    NavManager(userVM,ejercicioVM,registroVM,entrenamientoVM,gimnasioVM,subscripcionVM);
                }
            }
        }
    }
}

