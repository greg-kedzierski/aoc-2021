package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import kotlin.math.abs

object Day07 {
    private fun listOfInt(input: String) = input.split(",").map { it.toInt() }

    private fun sumOfN(n: Int) = n * (n + 1) / 2

    private fun minCostOfFuel(list: List<Int>, costFn: (Int) -> Int): Int =
        (list.reduce(Integer::min)..list.reduce(Integer::max)).let { range ->
            var min = Int.MAX_VALUE
            for (i in range) {
                var res = 0
                for (j in list) {
                    res += costFn(abs(i - j))
                    if (res > min) break
                }
                if (res < min) min = res
            }
            min
        }

    operator fun invoke() {
        val sampleInput = listOfInt(givenAdventInputFromFile("day07_sample.txt"))
        val input = listOfInt(givenAdventInputFromFile("day07.txt"))

        println("Part 1:")
        println(minCostOfFuel(sampleInput) { n -> n })
        println(minCostOfFuel(input) { n -> n })

        println("Part 2:")
        println(minCostOfFuel(sampleInput, ::sumOfN))
        println(minCostOfFuel(input, ::sumOfN))
    }
}

fun main() {
    Day07()
}
