package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day11 {
    private fun parse(input: String): Array<Array<Int>> =
        input.lines().map { it.toCharArray().map { ch -> Character.getNumericValue(ch) }.toTypedArray() }.toTypedArray()

    private fun adjacentLocs(map: Array<Array<Int>>, x: Int, y: Int): List<Point> =
        (-1..1).flatMap { i ->
            (-1..1).map { j -> Point(i + x, j + y) }
        }.filter { (x, y) ->
            x >= 0 && x < map.size && y >= 0 && y < map[0].size
        }.toList()

    private fun part1(map: Array<Array<Int>>): Int =
        (1..100).fold(0) { acc, _ ->
            acc + numberOfFlashedOctopus(map)
        }

    private tailrec fun part2(map: Array<Array<Int>>, size: Int = map.flatten().size, counter: Int = 0): Int =
        if (numberOfFlashedOctopus(map) == size)
            counter + 1
        else
            part2(map, size, counter + 1)

    private tailrec fun numberOfFlashedOctopus(
        map: Array<Array<Int>>,
        allFlashed: MutableSet<Point>,
        result: Int = 0
    ): Int {
        val flashed = mutableSetOf<Point>()
        map.forEachIndexed { i, r ->
            r.forEachIndexed { j, value ->
                if (value > 9) flashed += Point(i, j)
            }
        }
        return if (flashed.isEmpty())
            result
        else {
            allFlashed += flashed

            flashed.forEach { p ->
                adjacentLocs(map, p.first, p.second)
                    .filter { it !in allFlashed }
                    .forEach { map[it.first][it.second]++ }

                map[p.first][p.second] = 0
            }

            numberOfFlashedOctopus(map, allFlashed, result + flashed.size)
        }
    }

    private fun numberOfFlashedOctopus(map: Array<Array<Int>>): Int {
        map.forEach { r -> r.forEachIndexed { i, _ -> r[i]++ } }
        return numberOfFlashedOctopus(map, mutableSetOf())
    }

    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day11_sample.txt")
        val input = givenAdventInputFromFile("day11.txt")

        println("Part 1:")
        println(part1(parse(sampleInput)))
        println(part1(parse(input)))
        println("Part 2:")
        println(part2(parse(sampleInput)))
        println(part2(parse(input)))
    }
}

fun main() {
    Day11()
}
