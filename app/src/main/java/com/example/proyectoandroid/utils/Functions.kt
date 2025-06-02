package com.example.proyectoandroid.utils

fun getFila(fila: Int, dimension: Int, ocupacion: BooleanArray): List<Pair<Int, Boolean>> =
    (0 until dimension).map { col -> val index = fila * dimension + col; Pair(index, ocupacion[index]) }

fun getColumna(col: Int, dimension: Int, ocupacion: BooleanArray): List<Pair<Int, Boolean>> =
    (0 until dimension).map { fila -> val index = fila * dimension + col; Pair(index, ocupacion[index]) }

fun getDiagonalPrincipal(fila: Int, col: Int, dimension: Int, ocupacion: BooleanArray): List<Pair<Int, Boolean>> {
    var f = fila
    var c = col
    while (f > 0 && c > 0) { f--; c-- }
    val result = mutableListOf<Pair<Int, Boolean>>()
    while (f < dimension && c < dimension) {
        val idx = f * dimension + c
        result.add(Pair(idx, ocupacion[idx]))
        f++; c++
    }
    return result
}

fun getDiagonalSecundaria(fila: Int, col: Int, dimension: Int, ocupacion: BooleanArray): List<Pair<Int, Boolean>> {
    var f = fila
    var c = col
    while (f > 0 && c < dimension - 1) { f--; c++ }
    val result = mutableListOf<Pair<Int, Boolean>>()
    while (f < dimension && c >= 0) {
        val idx = f * dimension + c
        result.add(Pair(idx, ocupacion[idx]))
        f++; c--
    }
    return result
}

fun buscarEspacioLibre(
    linea: List<Pair<Int, Boolean>>,
    longitud: Int
): List<Int>? {
    val indices = linea.map { it.first }
    val estados = linea.map { it.second }

    for (i in 0..(estados.size - longitud)) {
        if ((i until i + longitud).all { !estados[it] }) {
            return indices.subList(i, i + longitud)
        }
    }
    return null
}