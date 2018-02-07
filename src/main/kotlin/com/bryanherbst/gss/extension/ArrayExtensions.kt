package com.bryanherbst.gss.extension

fun <T> Array<T>.random(): T {
    val index = (0 .. size - 1).random()
    return get(index)
}