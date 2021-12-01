package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day01 {
    private fun numberOfInc(lines: List<Int>): Int =
        (1 until lines.size).fold(0) { acc, i ->
            if (lines[i] > lines[i - 1])
                acc + 1
            else
                acc
        }

    private fun prepareInputForPart01(data: String): List<Int> =
        data.lines().map { it.toInt() }

    private fun prepareInputForPart02(data: String): List<Int> =
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
        println(numberOfInc(prepareInputForPart01(sampleInput)))
        println(numberOfInc(prepareInputForPart01(input)))

        println("Part 2:")
        println(numberOfInc(prepareInputForPart02(sampleInput)))
        println(numberOfInc(prepareInputForPart02(input)))
    }
}

fun main() {
    Day01()
}
