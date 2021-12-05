package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.Day05.LineDef.IntFunDef
import me.grzegorzk.adventofcode2021.Day05.LineDef.VerticalLine
import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day05 {

    data class Point(val x: Int, val y: Int)
    data class Line(val first: Point, val second: Point)

    sealed interface LineDef {
        data class IntFunDef(val a: Int, val b: Int) : LineDef {
            operator fun invoke(x: Int): Int = a * x + b
        }

        object VerticalLine : LineDef
    }

    private fun Line.toDef(): LineDef =
        (this.second.x - this.first.x).let { xDiff ->
            if (xDiff == 0)
                VerticalLine
            else {
                val a = (this.second.y - this.first.y) / xDiff // for our input `a` will always be -1 or 0 or 1
                val b = this.first.y - a * this.first.x
                IntFunDef(a, b)
            }
        }

    private fun parseInput(input: String): List<Line> =
        input.lines().map {
            it.split(" -> ").let { (first, second) ->
                Line(
                    first.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) },
                    second.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) }
                )
            }
        }

    private fun onlyVerticalOrHorizontal(lines: List<Line>): List<Line> =
        lines.filter { it.first.x == it.second.x || it.first.y == it.second.y }

    private fun countOverlappedLines(lines: List<Line>): Int {
        val maxVal = lines.map { maxOf(it.first.x, it.first.y, it.second.x, it.second.y) }.reduce(Integer::max)
        val map = lines.map { Pair(it, it.toDef()) }

        val result = Array(maxVal + 1) { IntArray(maxVal + 1) }

        for ((line, funDef) in map) {
            when (funDef) {
                is IntFunDef -> range(line.first.x, line.second.x).forEach { x -> result[x][funDef(x)] += 1 }
                VerticalLine -> range(line.first.y, line.second.y).forEach { y -> result[line.first.x][y] += 1 }
            }
        }

        return result.flatMap { it.toList() }.count { it > 1 }
    }

    private fun range(x: Int, y: Int): IntRange = if (x < y) x..y else y..x

    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day05_sample.txt")
        val input = givenAdventInputFromFile("day05.txt")

        val sampleLines = parseInput(sampleInput)
        val lines = parseInput(input)

        println("Part 1:")
        println(countOverlappedLines(onlyVerticalOrHorizontal(sampleLines)))
        println(countOverlappedLines(onlyVerticalOrHorizontal(lines)))

        println("Part 2:")
        println(countOverlappedLines(sampleLines))
        println(countOverlappedLines(lines))
    }
}

fun main() {
    Day05()
}
