package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day12 {

    data class Cave(val name: String) {
        val connections = mutableListOf<Cave>()
        val canVisitMultipleTimes: Boolean = name[0].isUpperCase()
    }

    private fun findNoOfPaths(
        node: Cave,
        visited: List<Cave>,
        canVisitTwice: Boolean,
        visitedSmallTwice: Boolean
    ): Int =
        if (node.name == "end")
            1
        else
            node.connections.sumOf {
                when {
                    it.canVisitMultipleTimes || it !in visited ->
                        findNoOfPaths(it, visited + it, canVisitTwice, visitedSmallTwice)
                    canVisitTwice && !visitedSmallTwice ->
                        findNoOfPaths(it, visited + it, canVisitTwice, visitedSmallTwice = true)
                    else -> 0
                }
            }

    private fun part1(cave: Cave): Int =
        findNoOfPaths(cave, emptyList(), canVisitTwice = false, visitedSmallTwice = false)

    private fun part2(cave: Cave): Int =
        findNoOfPaths(cave, emptyList(), canVisitTwice = true, visitedSmallTwice = false)

    private fun parse(input: String): Cave =
        input.lines().fold(mutableMapOf<String, Cave>()) { nodes, line ->
            val (from, to) = line.split('-')

            val fromCave =  nodes.computeIfAbsent(from) { Cave(from) }
            val toCave =  nodes.computeIfAbsent(to) { Cave(to) }

            if (fromCave.name != "start" && toCave.name != "end")
                toCave.connections += fromCave

            if (toCave.name != "start" && fromCave.name != "end")
                fromCave.connections += toCave

            nodes
        }["start"]!!

    operator fun invoke() {
        val sampleInput = parse(givenAdventInputFromFile("day12_sample.txt"))
        val input = parse(givenAdventInputFromFile("day12.txt"))

        println("Part 1:")
        println(part1(sampleInput))
        println(part1(input))
        println("Part 2:")
        println(part2(sampleInput))
        println(part2(input))
    }
}

fun main() {
    Day12()
}
