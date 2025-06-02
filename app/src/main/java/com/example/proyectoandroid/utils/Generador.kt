package com.example.proyectoandroid.utils

import com.example.proyectoandroid.network.ApiService
import io.ktor.client.*
import android.util.Log

class Generador {
    companion object{
        suspend fun generarSopa(client: HttpClient, dimension: Int): Pair<String, MutableList<String>> {
            val matriz = Matrix(dimension)
            var intentos = 0
            val maxIntentos = 100

            Log.i(TAG, "Iniciando generación de sopa de letras con dimensión: $dimension")
            while (intentos < maxIntentos) {
                val palabra = ApiService.obtenerPalabra(client)?.uppercase()

                Log.i(TAG, "Intento $intentos: Colocando palabra '$palabra' en la matriz")

                if (palabra != null && palabra.length <= dimension) {
                    val colocada = matriz.put(palabra)
                    if (colocada) {
                        Log.d(TAG, "Palabra insertada: $palabra")
                    } else{
                        Log.w(TAG, "La matriz ya se lleno: ${matriz.ocupacion.count { it }} celdas ocupadas")
                        return Pair(matriz.matrix.concatToString() , matriz.palabras)
                    }
                } else {
                    Log.w(TAG, "Palabra nula o demasiado larga: $palabra")
                    intentos++
                }
            }
            Log.i(TAG, "Generación de sopa completada. Intentos realizados: $intentos")
            return Pair(matriz.matrix.concatToString(), matriz.palabras)
        }

    }
}