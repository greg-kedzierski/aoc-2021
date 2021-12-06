package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day06 {
    private fun prepareInput(input: String) = input.split(",").map { it.toInt() }
        .groupBy { it }
        .mapValues { it.value.fold(0L) { acc, _ -> acc + 1 } }

    private fun lanternFishSpawns(days: Int, noOfFishPerDaysToSpawn: Map<Int, Long>): Long =
        noOfFishPerDaysToSpawn.toMutableMap().let { fish ->
            repeat(days) {
                val zeros = fish[0] ?: 0
                fish[0] = fish[1] ?: 0
                fish[1] = fish[2] ?: 0
                fish[2] = fish[3] ?: 0
                fish[3] = fish[4] ?: 0
                fish[4] = fish[5] ?: 0
                fish[5] = fish[6] ?: 0
                fish[6] = (fish[7] ?: 0) + zeros
                fish[7] = fish[8] ?: 0
                fish[8] = zeros
            }
            fish.values.sum()
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
