package com.example.proyectoandroid.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.gridlayout.widget.GridLayout

class SwipeCursor(
    private val sopa: String,
    private val dimension: Int,
    private val gridLayout: GridLayout,
    private val resaltador: Drawable?
) {

    private val palabraActual = StringBuilder()
    private val coordenadasVisitadas = mutableSetOf<Pair<Int, Int>>()
    private var direccion: Pair<Int, Int>? = null
    private var ultimaCelda: Pair<Int, Int>? = null

    // Tiempo mínimo entre procesamientos en ms (ajustable)
    private val throttleInterval = 50L
    private var lastMoveTime = 0L

    private fun getCeldaDesdeTouch(x: Float, y: Float): Pair<Int, Int>? {
        val fila = (y / (gridLayout.height / dimension)).toInt()
        val columna = (x / (gridLayout.width / dimension)).toInt()
        return if (fila in 0 until dimension && columna in 0 until dimension) {
            Pair(fila, columna)
        } else null
    }

    private fun obtenerLetra(fila: Int, columna: Int): Char {
        val index = fila * dimension + columna
        return sopa.getOrNull(index) ?: ' '
    }

    private fun getTextViewAt(fila: Int, columna: Int): TextView? {
        val index = fila * dimension + columna
        return gridLayout.getChildAt(index) as? TextView
    }

    private fun resaltarCelda(fila: Int, columna: Int, resaltador: Drawable?) {
        getTextViewAt(fila, columna)?.background = resaltador

    }

    private fun limpiarCeldasResaltadas() {
        coordenadasVisitadas.forEach { (fila, columna) ->
            getTextViewAt(fila, columna)?.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun getTouchListener(): View.OnTouchListener {
        return View.OnTouchListener { _, event ->
            val coords = getCeldaDesdeTouch(event.x, event.y)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    coords?.let {
                        direccion = null
                        ultimaCelda = it
                        coordenadasVisitadas.clear()
                        palabraActual.clear()
                        palabraActual.append(obtenerLetra(it.first, it.second))
                        coordenadasVisitadas.add(it)
                        resaltarCelda(it.first, it.second, resaltador)
                        lastMoveTime = SystemClock.elapsedRealtime()
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    // Throttle: solo procesar si ha pasado tiempo suficiente desde la última vez
                    val now = SystemClock.elapsedRealtime()
                    if (now - lastMoveTime < throttleInterval) {
                        return@OnTouchListener true
                    }
                    lastMoveTime = now

                    coords?.let { (fila, columna) ->
                        val actual = Pair(fila, columna)
                        if (coordenadasVisitadas.contains(actual)) return@let

                        ultimaCelda?.let { (lastFila, lastCol) ->
                            val dirFila = fila - lastFila
                            val dirCol = columna - lastCol

                            if (direccion == null && (dirFila != 0 || dirCol != 0)) {
                                direccion = Pair(dirFila.coerceIn(-1, 1), dirCol.coerceIn(-1, 1))
                            }

                            if (direccion != null && Pair(dirFila, dirCol) == direccion) {
                                palabraActual.append(obtenerLetra(fila, columna))
                                coordenadasVisitadas.add(actual)
                                resaltarCelda(fila, columna, resaltador)
                                ultimaCelda = actual
                                Log.d("SopaSwipe", "Letra: ${obtenerLetra(fila, columna)} → Palabra: $palabraActual")
                            }
                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    Log.i("SopaSwipe", "Palabra final: $palabraActual")
                    palabraActual.clear()
                    limpiarCeldasResaltadas()
                    coordenadasVisitadas.clear()
                    direccion = null
                    ultimaCelda = null
                }
            }
            true
        }
    }
}
