package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day08 {

    private fun parseInput(input: String): List<Pair<List<Set<Char>>, List<Set<Char>>>> =
        input.lines().map { line ->
            line.split(" | ").let { (left, right) ->
                Pair(left.split(" ").map { it.trim().toSet() },
                    right.split(" ").map { it.trim().toSet() })
            }
        }

    private fun mapSegmentsToDigit(patterns: List<Set<Char>>): Array<Set<Char>> {
        // ♪♪♪ it's a kind of magic xD ♪♪♪
        val one = patterns.first { it.size == 2 }
        val four = patterns.first { it.size == 4 }
        val seven = patterns.first { it.size == 3 }
        val eight = patterns.first { it.size == 7 }

        val two = patterns.first { it.size == 5 && (it - four).size == 3 }
        val three = patterns.first { it.size == 5 && (it - one).size == 3 }
        val five = patterns.first { it.size == 5 && it != two && it != three }

        val six = patterns.first { it.size == 6 && (it - one).size == 5 }
        val nine = patterns.first { it.size == 6 && (it - three).size == 1 }
        val zero = patterns.first { it.size == 6 && it != six && it != nine }

        return arrayOf(zero, one, two, three, four, five, six, seven, eight, nine)
    }

    private fun part1(lines: List<Pair<List<Set<Char>>, List<Set<Char>>>>) =
        lines.sumOf { line -> line.second.count { it.size in listOf(2, 4, 3, 7) } }

    private fun part2(lines: List<Pair<List<Set<Char>>, List<Set<Char>>>>): Int =
        lines.sumOf { (patterns, values) ->
            mapSegmentsToDigit(patterns).let { mapping ->
                values.map { mapping.indexOfFirst { i -> i == it } }.joinToString("").toInt()
            }
        }

    operator fun invoke() {
        val sampleInput = parseInput(givenAdventInputFromFile("day08_sample_2.txt"))
        val input = parseInput(givenAdventInputFromFile("day08.txt"))

        println("Part 1:")
        println(part1(sampleInput))
        println(part1(input))

        println("Part 2:")
        println(part2(sampleInput))
        println(part2(input))
    }
}

fun main() {
    Day08()
}
