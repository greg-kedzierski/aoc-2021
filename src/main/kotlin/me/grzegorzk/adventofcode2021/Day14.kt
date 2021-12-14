package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day14 {

    data class Insertion(val seq: String, val ins: String)

    private fun parse(input: String): Pair<String, List<Insertion>> {
        input.split("\r\n\r\n").let { (template, insertions) ->
            return Pair(
                template,
                insertions.lines().map {
                    it.split("->").let { (seq, ins) ->
                        Insertion(seq.trim(), ins.trim())
                    }
                })
        }
    }

    private fun solve(input: Pair<String, List<Insertion>>, steps: Int): Long {
        val (template, ins) = input

        val initialMap = (0..template.length - 2).fold(mutableMapOf<String, Long>()) { map, i ->
            map.also { map.compute("${template[i]}${template[i + 1]}") { _, v -> (v ?: 0) + 1 } }
        }

        val initialCounter = template.groupingBy { it }.eachCount()
            .map { (k, v) -> Pair(k.toString(), v.toLong()) }.toMap().toMutableMap()

        val b = (1..steps).fold(Pair(initialMap, initialCounter)) { (oldMap, counter), _ ->
            val newMap = mutableMapOf<String, Long>()
            ins.forEach {
                val noOfPairs = oldMap[it.seq]
                if (noOfPairs != null) {
                    newMap.compute("${it.seq[0]}${it.ins}") { _, v -> (v ?: 0) + noOfPairs }
                    newMap.compute("${it.ins}${it.seq[1]}") { _, v -> (v ?: 0) + noOfPairs }
                    counter.compute(it.ins) { _, v -> ((v ?: 0) + noOfPairs) }
                }
            }
            Pair(newMap, counter)
        }.second

        val min = b.entries.minByOrNull { it.value }?.value ?: 0
        val max = b.entries.maxByOrNull { it.value }?.value ?: 0

        return max - min
    }

    operator fun invoke() {
        val sampleInput: Pair<String, List<Insertion>> = parse(givenAdventInputFromFile("day14_sample.txt"))
        val input = parse(givenAdventInputFromFile("day14.txt"))

        println("Part 1:")
        println(solve(sampleInput, 10))
        println(solve(input, 10))
        println("Part 2:")
        println(solve(sampleInput, 40))
        println(solve(input, 40))
    }
}

fun main() {
    Day14()
}
