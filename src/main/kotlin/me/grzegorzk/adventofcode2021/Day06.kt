package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import java.util.Collections.rotate

object Day06 {
    private fun prepareInput(input: String) = input.split(",").map { it.toInt() }
        .groupBy { it }
        .mapValues { it.value.fold(0L) { acc, _ -> acc + 1 } }.let { sampleInput ->
            Array<Long>(9) { 0 }.also {
                sampleInput.forEach { p -> it[p.key] = p.value }
            }
        }

    private inline fun <reified T> Array<T>.rotate(distance: Int): Array<T> =
        toList().also { rotate(it, distance) }.toTypedArray()

    private fun lanternFishSpawns(days: Int, noOfFishPerDaysToSpawn: Array<Long>): Long =
        noOfFishPerDaysToSpawn.copyOf().let { fish ->
            (1..days).fold(fish) { acc, _ ->
                acc.rotate(-1).also { it[6] += it[8] }
            }.sum()
        }

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
