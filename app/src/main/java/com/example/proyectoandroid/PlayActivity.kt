package com.example.proyectoandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.example.proyectoandroid.databinding.ActivityPlayBinding
import com.example.proyectoandroid.utils.Generador
import com.example.proyectoandroid.utils.SwipeCursor
import com.google.android.material.textview.MaterialTextView
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch


class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayBinding
    private lateinit var client: HttpClient
    private val TAG = "SopaDebug"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = HttpClient(CIO) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        lifecycleScope.launch {
            Log.i(TAG, "Iniciando generador de sopa")
            val (matriz, palabras) = Generador.generarSopa(client, 8)
            Log.i(TAG, "Sopa generada: $matriz")
            Log.i(TAG, "Palabras encontradas: $palabras")

            binding.gridLetras.visibility = View.VISIBLE
            binding.wordList.visibility = View.VISIBLE
            binding.gridContainer.visibility = View.VISIBLE
            binding.btnHint.visibility = View.VISIBLE
            binding.barraProgreso.visibility = View.GONE
            binding.mensajeCarga.visibility = View.GONE

            usarDimensiones(8, matriz)
            llenarFlexbox(palabras)

            var drawable = ContextCompat.getDrawable(this@PlayActivity, R.drawable.celda_resaltada)
            val swipeCursor = SwipeCursor(matriz, 8, binding.gridLetras, drawable)
            binding.gridLetras.setOnTouchListener(swipeCursor.getTouchListener())

        }
    }

    fun goHome(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


    private fun llenarFlexbox(palabras: List<String>) {
        val flexbox = binding.wordList
        flexbox.removeAllViews()

        for (palabra in palabras) {
            val textView = MaterialTextView(this).apply {
                text = palabra
                setTextColor(ContextCompat.getColor(context, R.color.white))
                typeface = ResourcesCompat.getFont(context, R.font.fredoka_medium)
                textSize = 18f
                gravity = Gravity.CENTER

                width = palabra.length * 50
                height = 100
            }
            flexbox.addView(textView)
        }
    }



    private fun usarDimensiones(dims: Int, sopa: String) {
        Log.i("PLAY ACTIVITY", "Dimensiones: $dims, Sopa: $sopa")
        obtenerDimensionesGrid { w, h ->
            llenarGrid(w, h, dims, sopa)
        }
    }
    private fun obtenerDimensionesGrid(alObtener: (Int, Int) -> Unit) {
        binding.gridLetras.post {
            alObtener(
                binding.gridLetras.width,
                binding.gridLetras.height,
            )
        }
    }
    private fun llenarGrid(w: Int, h: Int, dims: Int, sopa: String) {
        val grid = binding.gridLetras
        Log.d(TAG, "altura : $h, ancho: $w, dimensiones: $dims, sopa: $sopa")
        grid.removeAllViews()
        grid.columnCount = dims
        grid.rowCount = dims

        for (i in 0 until dims * dims) {
            val textView = TextView(this).apply {
                text = sopa[i].toString()
                textSize = 18f
                gravity = Gravity.CENTER
                width = (w / dims)
                height = (w / dims)
                setTextAppearance(R.style.TextAppearance_Fredoka)
            }
            grid.addView(textView)
        }
    }

}