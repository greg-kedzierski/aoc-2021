package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile
import java.lang.Integer.min
import kotlin.math.abs

object Day07 {
    private fun listOfInt(input: String) = input.split(",").map { it.toInt() }

    private fun sumOfN(n: Int) = n * (n + 1) / 2

    private fun minCostOfFuelFun(list: List<Int>, costFn: (Int) -> Int): Int =
        (list.reduce(Integer::min)..list.reduce(Integer::max)).let { range ->
            range.fold(Int.MAX_VALUE) { min, i ->
                min(min, list.fold(0) { sum, j -> costFn(abs(i - j)) + sum })
            }
        }

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
        println(minCostOfFuelFun(sampleInput) { n -> n })
        println(minCostOfFuel(input) { n -> n })
        println(minCostOfFuelFun(input) { n -> n })

        println("Part 2:")
        println(minCostOfFuel(sampleInput, ::sumOfN))
        println(minCostOfFuelFun(sampleInput, ::sumOfN))
        println(minCostOfFuel(input, ::sumOfN))
        println(minCostOfFuelFun(input, ::sumOfN))
    }
}

fun main() {
    Day07()
}
