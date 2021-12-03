package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import java.lang.Character.getNumericValue

object Day03 {
    private fun parseInput(sampleInput: String): List<List<Int>> = sampleInput.lines().map { line ->
        line.toCharArray().map { getNumericValue(it) }
    }

    private fun part1(input: List<List<Int>>): Int =
        input
            .fold(Array(input[0].size) { 0 }) { acc, row ->
                row.indices.fold(acc) { ac, i ->
                    ac.also { ac[i] += row[i] and 1 }
                }
            }
            .map { it > input.size / 2 }
            .fold(Pair(0, 0)) { (gamma, epsilon), moreBitsSet ->
                Pair((gamma shl 1) + (if (moreBitsSet) 1 else 0), (epsilon shl 1) + (if (moreBitsSet) 0 else 1))
            }
            .let { it.first * it.second }


    private fun part2(input: List<List<Int>>): Int =
        input[0].size.let { rows ->
            lifeSupportRating(rows, input, ::oxygenGeneratorRatingWinnerDigit) *
                    lifeSupportRating(rows, input, ::co2ScrubberRatingWinnerDigit)
        }


    private fun lifeSupportRating(rows: Int, input: List<List<Int>>, funToComputeWinningDigit: (Int, Int) -> Int): Int =
        (0 until rows).fold(input) { inputList, i ->
            when (inputList.size) {
                1 -> inputList
                else ->
                    inputList.sumOf { it[i] }.let { sum ->
                        inputList.filter { it[i] == funToComputeWinningDigit(sum, inputList.size) }
                    }
            }
        }.single().fold(0) { acc, it -> (acc shl 1) + it }


    private fun oxygenGeneratorRatingWinnerDigit(sum: Int, size: Int) =
        if (sum >= size / 2.toDouble()) 1 else 0

    private fun co2ScrubberRatingWinnerDigit(sum: Int, size: Int) =
        if (sum >= size / 2.toDouble()) 0 else 1

    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day03_sample.txt")
        val input = givenAdventInputFromFile("day03.txt")

        println("Part 1:")
        println(part1(parseInput(sampleInput)))
        println(part1(parseInput(input)))

        println("Part 2:")
        println(part2(parseInput(sampleInput)))
        println(part2(parseInput(input)))
    }
}

fun main() {
    Day03()
}
