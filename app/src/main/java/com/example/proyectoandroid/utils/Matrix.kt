package com.example.proyectoandroid.utils

import android.annotation.SuppressLint
import android.util.Log

class Matrix(private val dimension: Int) {

    private val matriz: Array<CharArray> = Array(dimension) { CharArray(dimension) { ' ' } }
    var libres: Int = dimension * dimension
    val palabras: MutableList<String> = mutableListOf()

    operator fun get(cursor: Cursor): Char {
        return if (cursor.esValido(dimension)) {
            matriz[cursor.fila][cursor.columna]
        } else {
            ' '
        }
    }

    operator fun set(cursor: Cursor, value: Char) {
        if (cursor.esValido(dimension)) {
            if (matriz[cursor.fila][cursor.columna] == ' ') {
                libres -= 1
            }
            matriz[cursor.fila][cursor.columna] = value
        }
    }

    fun put(palabra: String): Boolean {
        val x = (0..<dimension).random()
        val y = (0..<dimension).random()
        val cursor = Cursor(x, y)

        val largo = palabra.length
        cursor.next(largo)
        if (!cursor.esValido(dimension)) return false
        cursor.next(-largo)

        var restantes = largo
        for (indice in 0..<largo) {
            val actual = this[cursor]
            if (actual == ' ' || actual == palabra[indice]) {
                this[cursor] = palabra[indice]
                restantes--
            }
            cursor.next()
        }

        if (restantes == 0) {
            palabras.add(palabra)
        }

        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        val vocales = listOf('A', 'E', 'I', 'O', 'U')

        for (i in 0..<dimension) {
            val fila = matriz[i].map { c ->
                if (c == ' ') vocales.random() else c
            }.joinToString("")
            sb.append(String.format("%s", fila))
        }

        return sb.toString()
    }

}