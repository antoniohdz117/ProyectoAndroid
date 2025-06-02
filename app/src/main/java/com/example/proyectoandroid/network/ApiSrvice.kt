package com.example.proyectoandroid.network

import com.example.proyectoandroid.data.PalabraResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import android.util.Log
import com.example.proyectoandroid.utils.TAG

object ApiService {
    suspend fun obtenerPalabra(client: HttpClient): String? {
        return try {

            Log.d(TAG, "Realizando solicitud para obtener una palabra aleatoria")

            val response = client.get("https://demapi.colmex.mx/api/Palabras/GetPalabraAleatoriaFormada/") {
                headers {
                    append("token", "j1FUX2y7DklLgclZs5Nm6WaUjGcAgoR1")
                    append("cliente", "appsunam")
                }
            }
            val palabras: List<PalabraResponse> = response.body()
            val entrada = palabras.firstOrNull()?.entrada

            Log.d(TAG, "palabra obtenida: $entrada")
            entrada
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener la palabra: ${e.message}")
            null
        }
    }
}
