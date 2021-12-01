package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day01 {
    private fun numberOfInc(lines: List<Int>): Int =
        lines.zipWithNext { a, b -> a < b }.count { it }

    private fun inputPart01(data: String): List<Int> =
        data.lines().map { it.toInt() }

    private fun inputPart02(data: String): List<Int> =
        data.lines().map { it.toInt() }
            .let { lines ->
                (0..lines.size - 3).map {
                    lines[it] + lines[it + 1] + lines[it + 2]
                }
            }

    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day01_sample.txt")
        val input = givenAdventInputFromFile("day01.txt")

        println("Part 1:")
        println(numberOfInc(inputPart01(sampleInput)))
        println(numberOfInc(inputPart01(input)))

        println("Part 2:")
        println(numberOfInc(inputPart02(sampleInput)))
        println(numberOfInc(inputPart02(input)))

    }
}

fun main() {
    Day01()
}
