package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.Day02.Dir.*
import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day02 {
    enum class Dir { forward, down, up }

    data class Instruction(val dir: Dir, val mov: Int)
    data class Position(val x: Int = 0, val y: Int = 0, val aim: Int = 0)

    private fun asListOfInstructions(data: String): List<Instruction> =
        data.lines()
            .map { it.split(" ") }
            .map { Instruction(Dir.valueOf(it[0]), it[1].toInt()) }


    private fun algForPart1(instructions: List<Instruction>): Int =
        instructions.fold(Position()) { pos, inst ->
            when (inst.dir) {
                forward -> pos.copy(x = pos.x + inst.mov)
                down -> pos.copy(y = pos.y + inst.mov)
                up -> pos.copy(y = pos.y - inst.mov)
            }
        }.let { it.x * it.y }

    private fun algForPart2(instructions: List<Instruction>): Int =
        instructions.fold(Position()) { pos, inst ->
            when (inst.dir) {
                forward -> pos.copy(x = pos.x + inst.mov, y = pos.y + inst.mov * pos.aim)
                down -> pos.copy(aim = pos.aim + inst.mov)
                up -> pos.copy(aim = pos.aim - inst.mov)
            }
        }.let { it.x * it.y }

    operator fun invoke() {
        val sampleInput = asListOfInstructions(givenAdventInputFromFile("day02_sample.txt"))
        val input = asListOfInstructions(givenAdventInputFromFile("day02.txt"))

        println("Part 1:")
        println(algForPart1(sampleInput))
        println(algForPart1(input))

        println("Part 2:")
        println(algForPart2(sampleInput))
        println(algForPart2(input))
    }
}

fun main() {
    Day02()
}
