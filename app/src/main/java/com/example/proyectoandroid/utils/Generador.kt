package com.example.proyectoandroid.utils

import com.example.proyectoandroid.network.ApiService
import io.ktor.client.*
import android.util.Log

class Generador {
    companion object{
        suspend fun generarSopa(client: HttpClient, dimension: Int): Pair<String, List<String>> {
            val matriz = Matrix(dimension)
            var intentos = 0
            val maxIntentos = 100

            Log.i(TAG, "Iniciando generación de sopa de letras con dimensión: $dimension")
            while (matriz.libres > 0 && intentos < maxIntentos) {
                val palabra = ApiService.obtenerPalabra(client)?.uppercase()

                Log.i(TAG, "Intento $intentos: Colocando palabra '$palabra' en la matriz")

                if (palabra != null && palabra.length <= dimension) {
                    val colocada = matriz.put(palabra)
                    if (colocada) {
                        Log.d(TAG, "Palabra insertada: $palabra")
                    } else{
                        Log.w(TAG, "No se pudo colocar la palabra: $palabra")
                        intentos++
                    }
                } else {
                    Log.w(TAG, "Palabra nula o demasiado larga: $palabra")
                    intentos++
                }
            }
            Log.i(TAG, "Generación de sopa completada. Intentos realizados: $intentos ${matriz.libres} espacios libres restantes.")
            return Pair(matriz.toString(), matriz.palabras)
        }

    }
}