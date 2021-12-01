package me.grzegorzk.adventofcode2021.utils

fun givenAdventInputFromFile(path: String): String =
    object {}.javaClass.getResource("/me/grzegorzk/adventofcode2021/$path")!!.readText()
