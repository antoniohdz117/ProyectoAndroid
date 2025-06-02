package com.example.proyectoandroid.utils

class Matrix(dimension: Int) {

    val matrix: CharArray = CharArray(dimension*dimension) { ' ' }
    val palabras: MutableList<String> = mutableListOf()
    val ocupacion: BooleanArray = BooleanArray(dimension*dimension) { false }
    val dims = dimension


    fun put(
        palabra: String,
    ): Boolean {

        val direcciones = Direccion.entries.toMutableList()
        val posicionesLibres = ocupacion.withIndex().filter { !it.value }.map { it.index }

        if (posicionesLibres.isEmpty()) return false

        val index = posicionesLibres.random()
        val fila = index / dims
        val col = index % dims

        direcciones.shuffle()

        for (dir in direcciones) {
            val linea = when (dir) {
                Direccion.HORIZONTAL -> getFila(fila, dims, ocupacion)
                Direccion.VERTICAL -> getColumna(col, dims, ocupacion)
                Direccion.DIAGONAL_PPAL -> getDiagonalPrincipal(fila, col, dims, ocupacion)
                Direccion.DIAGONAL_SEC -> getDiagonalSecundaria(fila, col, dims, ocupacion)
            }

            val indicesDisponibles = buscarEspacioLibre(linea, palabra.length)

            if (indicesDisponibles != null) {
                val invertir = listOf(true, false).random()
                val letras = if (invertir) palabra.reversed() else palabra

                for ((i, idx) in indicesDisponibles.withIndex()) {
                    matrix[idx] = letras[i]
                    ocupacion[idx] = true
                }
                palabras.add(palabra)
                return true
            }
        }
        matrix[index] = ('A'..'Z').random()
        ocupacion[index] = true
        return true
    }

}