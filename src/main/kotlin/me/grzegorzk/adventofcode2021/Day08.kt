package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day08 {

    private fun parseInput(input: String): List<Pair<List<String>, List<String>>> =
        input.lines().map { line ->
            line.split(" | ").let { (left, right) ->
                Pair(left.split(" ").map { it.trim() }, right.split(" ").map { it.trim() })
            }
        }

    private fun part1(lines: List<Pair<List<String>, List<String>>>) =
        lines.sumOf { line -> line.second.count { it.length in listOf(2, 4, 3, 7) } }

    operator fun invoke() {
        val sampleInput = parseInput(givenAdventInputFromFile("day08_sample_2.txt"))
        val input = parseInput(givenAdventInputFromFile("day08.txt"))

        println("Part 1:")
        println(part1(sampleInput))
        println(part1(input))

        println("Part 2:")
    }
}

fun main() {
    Day08()
}
