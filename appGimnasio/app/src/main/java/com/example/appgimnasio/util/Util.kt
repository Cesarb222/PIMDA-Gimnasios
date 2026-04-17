package com.example.appgimnasio.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.appgimnasio.Model.EjerciciosModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


//Clase creada para reutilizar varias funciones o que una funcion pueda ser util
class Util {
    //Funcion para cambiar el color del estado, en la vista del calcular IMC dependiendo si esta seleccionado o no
    // dependiendo si esta seleccionado o no
    fun seleccionarColor(estado: Boolean): Color{
        if(estado) return Color(0xFF0F4C5C)
        else return Color(0xFF1A677E)
    }

    //Funcion para cambiar el color de los iconos, en el menu de navegacion
    // dependiendo si esta seleccionado o no
    fun colorIcon(destino: String, seleccion:String):Color{
        if (destino.equals(seleccion)) return Color.White
        return Color.Gray
    }
    //Funcion para calcular el imc segun el peso y altura
    fun calcularImc(altura: Int, peso: Float): Float{
        var alturaMetros = altura/100f
        return peso / (alturaMetros * alturaMetros)
    }
    //Funcion que nos retorna el valor segun lo que nos retorne la funcion calcularImc
    fun resultadoImc(altura: Int, peso: Float): String{
        val result = this.calcularImc(altura,peso);
        when{
            result < 18.5 -> return "Bajo peso"
            result < 25.0 -> return "Peso normal"
            result < 30.0 -> return "Sobrepeso"
            else -> return "Obesidad"
        }
    }

    //Funcion para comparar las fechas en este caso los años para utilizarlo en el registro.
    // nos retorna un booleano dependiendo de si es mayor a 16 o no
    fun compararFechas(a: String): Boolean {

        //le damos un formato ya que en el datepicker nos viene con YYYY/MM/DD
        val formato = DateTimeFormatter.ofPattern("yyyy/MM/dd")

        val fecha = LocalDate.parse(a, formato)

        val edad = ChronoUnit.YEARS.between( fecha, LocalDate.now())

        if (edad >= 16) return true
        return false

    }

    //Funcion para eliminar los duplicados de una lista en caso de que tenga duplicados las lista,
    //Nos retorna una nueva lista de Strings
    fun eliminarDuplicados(list: List<EjerciciosModel>, propiedad: String): List<String>{
        var listaSinDuplicar: MutableList<String> = mutableListOf()
        for (i in list.indices){
            if (!listaSinDuplicar.contains(list[i].nombre) && propiedad.equals("nombre")) listaSinDuplicar.add(list[i].nombre)
            if (!listaSinDuplicar.contains(list[i].grupo_muscular) && propiedad.equals("musculo")) listaSinDuplicar.add(list[i].grupo_muscular)
        }
        return listaSinDuplicar
    }

    //Funcion para devolver un ejercicio
    fun devolverEjercicio(list: List<EjerciciosModel>,nombreEjercicio: String): EjerciciosModel?{
        for (i in list.indices){
            if (list[i].nombre.equals(nombreEjercicio)) return list[i]
        }
        return null
    }


}