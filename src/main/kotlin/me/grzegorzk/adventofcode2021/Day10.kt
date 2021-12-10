package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day10 {

    private fun parse(input: String): List<String> =
        input.lines()

    private val paraMatch = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

    private fun scoreCorrupted(line: String, pointsMap: Map<Char, Int>): Int =
        ArrayDeque<Char>().let { deq ->
            line.fold(' ') { acc, c ->
                if (c in listOf('(', '[', '{', '<'))
                    deq.addFirst(c).let { acc }
                else if (deq.removeFirst() != paraMatch[c])
                    c
                else
                    acc
            }.let { pointsMap[it] ?: 0 }
        }

    private fun scoreIncomplete(line: String, pointsMap: Map<Char, Int>): Long =
        ArrayDeque<Char>().let { deq ->
            line.fold(deq) { acc, c ->
                if (c in listOf('(', '[', '{', '<'))
                    acc.add(c).let { acc }
                else if (deq.removeLast() != paraMatch[c])
                    return 0
                else
                    acc
            }
        }.let { deq ->
            deq.map { pointsMap[it]!! }.foldRight(0L) { acc, total ->
                5 * total + acc
            }
        }

    private fun part1(lines: List<String>): Int =
        lines.fold(0) { acc, line ->
            acc + scoreCorrupted(
                line,
                pointsMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
            )
        }

    private fun part2(lines: List<String>): Long =
        lines
            .map { scoreIncomplete(it, pointsMap = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)) }
            .filter { it != 0L }
            .sorted()
            .let { it[it.size / 2] }


    operator fun invoke() {
        val sampleInput = parse(givenAdventInputFromFile("day10_sample.txt"))
        val input = parse(givenAdventInputFromFile("day10.txt"))

        println("Part 1:")
        println(part1(sampleInput))
        println(part1(input))
        println("Part 2:")
        println(part2(sampleInput))
        println(part2(input))
    }
}

fun main() {
    Day10()
}
