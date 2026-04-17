package com.example.appgimnasio.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appgimnasio.ViewModels.EjercicioViewModels
import com.example.appgimnasio.ViewModels.EntrenamientoViewModel
import com.example.appgimnasio.ViewModels.GimnasioViewModel
import com.example.appgimnasio.ViewModels.RegistroViemModel
import com.example.appgimnasio.ViewModels.SubscripcionViewModel

import com.example.appgimnasio.ViewModels.UserViewModel
import com.example.appgimnasio.Views.AccessView
import com.example.appgimnasio.Views.ComprarSubscripcion
import com.example.appgimnasio.Views.Ejercicios.EjercicioInfoView
import com.example.appgimnasio.Views.Entreno.AddEntrenoView
import com.example.appgimnasio.Views.Registros.AddRegistroView
import com.example.appgimnasio.Views.Entreno.AddView
import com.example.appgimnasio.Views.Registros.AllRegistrosView
import com.example.appgimnasio.Views.Ejercicios.EjerciciosView
import com.example.appgimnasio.Views.Entreno.EntrenoInfoView
import com.example.appgimnasio.Views.HomeView
import com.example.appgimnasio.Views.Imc
import com.example.appgimnasio.Views.Registros.InfoRegistroView
import com.example.appgimnasio.Views.UserViews.LoginView
import com.example.appgimnasio.Views.PerfilView
import com.example.appgimnasio.Views.UserViews.RecuperarPassword
import com.example.appgimnasio.Views.UserViews.RegistroView
import com.example.appgimnasio.Views.Registros.ViewRegistrosView
import com.example.appgimnasio.Views.UserViews.AdministracionView
import com.example.appgimnasio.Views.UserViews.CambiarContraseña
import com.example.appgimnasio.Views.UserViews.PonerCodigo

//NavController para el manejo de rutas
@Composable
fun NavManager(userVM: UserViewModel,ejercicioVM: EjercicioViewModels,
               registroVM: RegistroViemModel,entrenamientoVM: EntrenamientoViewModel,
               gimnasioVM: GimnasioViewModel,subscripcionVM: SubscripcionViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){

        composable(route = "imc_calculator"){
            Imc(navController)
        }
        composable(route = "Home") {
            HomeView(navController,userVM)
        }
        composable(route = "Perfil") {
            PerfilView(navController,userVM)
        }
        composable(route = "login") {
            LoginView(navController,userVM)
        }
        composable(route = "Registro") {
            RegistroView(navController,userVM)
        }
        composable(route = "Acceso"){
            AccessView(navController,userVM)
        }
        composable(route = "Publicar"){
            AddRegistroView(navController,userVM,ejercicioVM,registroVM)
        }
        composable("MisRegistros"){
            ViewRegistrosView(navController,userVM,ejercicioVM,registroVM)
        }
        composable("Publicaciones"){
            AllRegistrosView(navController,userVM,ejercicioVM,registroVM)
        }
        composable("MisEntrenos"){
            AddView(navController,entrenamientoVM)
        }
        composable("añadirEntreno"){
            AddEntrenoView(navController,ejercicioVM,entrenamientoVM)
        }

        composable("ejercicios"){
            EjerciciosView(navController,ejercicioVM)
        }

        composable(route="recuperar"){
            RecuperarPassword(navController,userVM)
        }

        composable(route="comprarSubscripcion"){
            ComprarSubscripcion(navController,userVM,gimnasioVM,subscripcionVM)
        }

        composable(route="administrar"){
            AdministracionView(navController,userVM,gimnasioVM)
        }

        composable(route="ponerCodigo/{email}",
            arguments = listOf(
                navArgument("email") {type = NavType.StringType}
            )){ it->
            val email = it.arguments?.getString("email") ?: ""
            PonerCodigo(navController,userVM,email)
        }

        composable(route="modificarPassword/{email}",
            arguments = listOf(
                navArgument("email") {type = NavType.StringType}
            )){ it->
            val email = it.arguments?.getString("email") ?: ""
            CambiarContraseña(navController,userVM,email)
        }

        //Ruta con parametros o argumentos
        composable(
            route="registroInfo/{id}",
            arguments = listOf(
                //le pasamos la lista y recopilamos el argumento el y el tipo
                navArgument("id") {type = NavType.LongType}
            )
        ){ argumento->
            //lo mostramos y se lo pasamos a la funcion
            val id = argumento.arguments?.getLong("id") ?:0
            InfoRegistroView(navController,registroVM,id)
        }

        composable(
            route="entreno/{id}",
            arguments = listOf(
                //le pasamos la lista y recopilamos el argumento el y el tipo
                navArgument("id") {type = NavType.LongType},
            )
        ){ argumento->
            //lo mostramos y se lo pasamos a la funcion
            val id = argumento.arguments?.getLong("id") ?:0
            EntrenoInfoView(navController,entrenamientoVM,id)
        }

        composable(
            route="ejercicio/{nombre}",
            arguments = listOf(
                //le pasamos la lista y recopilamos el argumento el y el tipo
                navArgument("nombre") {type = NavType.StringType},
            )
        ){ argumento->
            //lo mostramos y se lo pasamos a la funcion
            val nombre = argumento.arguments?.getString("nombre") ?:""
            EjercicioInfoView(navController,ejercicioVM,nombre)
        }
    }
}