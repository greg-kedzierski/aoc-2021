package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import java.util.*
import java.util.Comparator.comparing

object Day15 {

    data class Node(val x: Int, val y: Int, val w: Int, var dist: Int = Int.MAX_VALUE) {
        fun adjacentLocs(xMax: Int, yMax: Int) = listOf(
            Pair(x - 1, y), Pair(x + 1, y),
            Pair(x, y - 1), Pair(x, y + 1)
        ).filter { (x, y) -> x >= 0 && x < xMax && y >= 0 && y < yMax }
    }

    private fun dijkstra(array: List<List<Int>>): Int {
        val xMax = array[0].size
        val yMax = array.size

        val nodes = array.mapIndexed { y, l -> l.mapIndexed { x, w -> Node(x, y, w) } }

        val queue = PriorityQueue<Node>(comparing { it.dist })
        queue.add(nodes[0][0].also { nodes[0][0].dist = 0 })

        while (!queue.isEmpty()) {
            val n = queue.poll()
            n.adjacentLocs(xMax, yMax)
                .map { (x, y) -> nodes[y][x] }
                .forEach {
                    val distance = n.dist + it.w
                    if (distance < it.dist) {
                        it.dist = distance
                        queue.add(it)
                    }
                }
        }

        return nodes[yMax - 1][xMax - 1].dist
    }

    private fun biggerMap(originalMap: List<List<Int>>): List<List<Int>> =
        (0..4).flatMap { n ->
            originalMap
                .map { line -> (0..4).map { n -> incBy(line, n) }.flatten() }
                .map { incBy(it, n) }
        }

    private fun incBy(list: List<Int>, n: Int): List<Int> =
        list.map {
            (it + n).let { sum -> if (sum < 10) sum else (sum % 10) + 1 }
        }

    private fun parse(input: String): List<List<Int>> =
        input.lines().map { it.toCharArray().map { d -> d.digitToInt() } }

    operator fun invoke() {
        val sampleInput = parse(givenAdventInputFromFile("day15_sample.txt"))
        val input = parse(givenAdventInputFromFile("day15.txt"))

        println("Part 1:")
        println(dijkstra(sampleInput))
        println(dijkstra(input))
        println("Part 2:")
        println(dijkstra(biggerMap(sampleInput)))
        println(dijkstra(biggerMap(input)))
    }
}

fun main() {
    Day15()
}
