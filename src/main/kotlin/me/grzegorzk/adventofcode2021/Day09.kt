package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import java.lang.Character.getNumericValue

typealias Point = Pair<Int, Int>

object Day09 {

    private fun parse(input: String): List<List<Int>> =
        input.lines().map { it.toCharArray().map { ch -> getNumericValue(ch) } }

    private fun adjacentLocs(map: List<List<Int>>, x: Int, y: Int): List<Point> =
        listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
            .filter { (x, y) -> x >= 0 && x < map.size && y >= 0 && y < map[0].size }

    private fun part1(map: List<List<Int>>): Int =
        map.foldIndexed(0) { i, acc, row ->
            acc + row.foldIndexed(0) { j, rowAcc, cell ->
                if (adjacentLocs(map, i, j).all { (x, y) -> map[x][y] > cell })
                    rowAcc + cell + 1
                else
                    rowAcc
            }
        }

    private fun checkAdjacentLocs(map: List<List<Int>>, p: Point, visited: MutableSet<Point>): Int =
        if (map[p.first][p.second] == 9 || p in visited)
            0
        else
            adjacentLocs(map, p.first, p.second).sumOf {
                checkAdjacentLocs(map, it, visited.add(p).let { visited })
            } + 1

    private fun part2(map: List<List<Int>>): Int {
        val visited = mutableSetOf<Point>()

        return map
            .flatMapIndexed { x, row -> row.mapIndexed { y, _ -> checkAdjacentLocs(map, Pair(x, y), visited) } }
            .sortedDescending().let { it[0] * it[1] * it[2] }
    }

    operator fun invoke() {
        val sampleInput = parse(givenAdventInputFromFile("day09_sample.txt"))
        val input = parse(givenAdventInputFromFile("day09.txt"))

        println("Part 1:")
        println(part1(sampleInput))
        println(part1(input))
        println("Part 2:")
        println(part2(sampleInput))
        println(part2(input))
    }
}


fun main() {
    Day09()
}
