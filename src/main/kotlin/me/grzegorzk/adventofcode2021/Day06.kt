package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import java.util.Collections.rotate

object Day06 {
    private fun prepareInput(input: String) =
        input.split(",").map { it.toInt() }
            .fold(Array<Long>(9) { 0 }) { acc, i ->
                acc.also { acc[i]++ }
            }


    private inline fun <reified T> Array<T>.rotate(distance: Int): Array<T> =
        toList().also { rotate(it, distance) }.toTypedArray()

    private fun lanternFishSpawns(days: Int, noOfFishPerDaysToSpawn: Array<Long>): Long =
        (1..days).fold(noOfFishPerDaysToSpawn) { acc, _ ->
            acc.rotate(-1).also { it[6] += it[8] }
        }.sum()

    operator fun invoke() {
        val sampleInput = prepareInput(givenAdventInputFromFile("day06_sample.txt"))
        val input = prepareInput(givenAdventInputFromFile("day06.txt"))

        println("Part 1:")
        println(lanternFishSpawns(80, sampleInput))
        println(lanternFishSpawns(80, input))

        println("Part 2:")
        println(lanternFishSpawns(256, sampleInput))
        println(lanternFishSpawns(256, input))
    }
}

fun main() {
    Day06()
}
