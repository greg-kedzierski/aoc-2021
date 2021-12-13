package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.Day13.Fold.X
import me.grzegorzk.adventofcode2021.Day13.Fold.Y
import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day13 {
    private sealed class Fold(val value: Int) {
        class X(value: Int) : Fold(value)
        class Y(value: Int) : Fold(value)

        override fun toString(): String = "${javaClass.simpleName}=$value"
    }

    private fun parse(input: String): Pair<Array<BooleanArray>, List<Fold>> {
        input.split("\r\n\r\n").let { (map, folds) ->
            val points = map.lines().map { it.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
            val size = points.fold(Pair(0, 0)) { acc, p ->
                Pair(maxOf(p.first, acc.first), maxOf(p.second, acc.second))
            }
            return Pair(
                points.fold(Array(size.second + 1) { _ -> BooleanArray(size.first + 1) }) { acc, p ->
                    acc.also { acc[p.second][p.first] = true }
                },
                folds.lines().map {
                    it.split(" ")[2].split("=").let { (axis, value) ->
                        when (axis) {
                            "x" -> X(value.toInt())
                            "y" -> Y(value.toInt())
                            else -> throw RuntimeException()
                        }
                    }
                })
        }
    }

    private fun folding(folds: List<Fold>, map: Array<BooleanArray>): Array<BooleanArray> =
        folds.fold(map) { acc, fold ->
            when (fold) {
                is X -> Array(acc.size) { _ -> BooleanArray(acc[0].size / 2) }.also { newArray ->
                    for (i in newArray.indices) {
                        (fold.value + 1 until acc[0].size).forEach {
                            val second = (((acc[0].size * 2) - it) % acc[0].size) - 1
                            newArray[i][second] = acc[i][second] || acc[i][it]
                        }
                    }
                }
                is Y -> Array(acc.size / 2) { _ -> BooleanArray(acc[0].size) }.also { newArray ->
                    (fold.value + 1 until acc.size).forEach {
                        val second = (((acc.size * 2) - it) % acc.size) - 1
                        newArray[second] = acc[it].zip(acc[second]) { a, b -> a || b }.toBooleanArray()
                    }
                }
            }
        }

    private fun part1(input: String): Array<BooleanArray> =
        parse(input).let { (map, folds) -> folding(folds.take(1), map) }

    private fun part2(input: String): Array<BooleanArray> =
        parse(input).let { (map, folds) -> folding(folds, map) }


    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day13_sample.txt")
        val input = givenAdventInputFromFile("day13.txt")

        val p1 = part1(input)
        val p2 = part2(input)

        println("Part 1:")
        println(p1.sumOf { it.count { it } })
        println("Part 2:")
        println(p2.sumOf { it.count { it } })
        p2.forEach {
            it.forEach { cell -> print(if (cell) "#" else " ") }
            println()
        }
    }
}

fun main() {
    Day13()
}
