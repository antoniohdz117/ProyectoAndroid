package com.example.proyectoandroid.utils

data class Cursor(var fila: Int, var columna: Int) {
    var avanX: Int
    var avanY: Int

    init {
        val direcciones = listOf(-1, 0, 1)
        avanX = direcciones.random()
        avanY = direcciones.random()
        if (avanX == 0 && avanY == 0) {
            avanX = 1
        }
    }

    fun next(pasos: Int = 1) {
        fila += pasos * avanX
        columna += pasos * avanY
    }

    fun esValido(dimension: Int): Boolean {
        return fila in 0..<dimension && columna in 0..<dimension
    }

    override fun toString(): String {
        return "[$fila $avanX, $columna $avanY]"
    }
}