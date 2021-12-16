package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.Day16.Packet.Literal
import me.grzegorzk.adventofcode2021.Day16.Packet.Operator
import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day16 {
    enum class Op(val id: Int, val eval: (List<Long>) -> Long) {
        Sum(0, { it.sum() }),
        Product(1, { it.fold(1) { acc, i -> acc * i } }),
        Min(2, { v -> v.minOf { it } }),
        Max(3, { v -> v.maxOf { it } }),
        Gt(5, { if (it[0] > it[1]) 1 else 0 }),
        Lt(6, { if (it[0] < it[1]) 1 else 0 }),
        Eq(7, { if (it[0] == it[1]) 1 else 0 })
    }

    sealed class Packet(val version: Int) {
        class Literal(val value: Long, version: Int) : Packet(version)
        class Operator(val packets: List<Packet>, version: Int, val type: Op) : Packet(version)
    }

    private fun sumOfVersions(packet: Packet): Int = when (packet) {
        is Literal -> packet.version
        is Operator -> with(packet) { version + packets.sumOf { sumOfVersions(it) } }
    }

    private fun valueOf(packet: Packet): Long = when (packet) {
        is Literal -> packet.value
        is Operator -> (packet.packets.map { valueOf(it) }).let { v -> packet.type.eval(v) }
    }

    private fun binListToNumber(input: List<Int>): Long = input.fold(0L) { acc, bit -> (acc shl 1) + bit }

    private fun toPacket(input: List<Int>): Pair<Packet, Int> {
        val version = binListToNumber(input.subList(0, 3)).toInt()
        val type = binListToNumber(input.subList(3, 6)).toInt()
        return if (type == 4) {
            parseLiteral(input, version)
        } else {
            val packetsWithIdx = if (input[6] == 0) parseOperator0(input) else parseOperator1(input)
            Pair(Operator(packetsWithIdx.first, version, Op.values().first { it.id == type }), packetsWithIdx.second)
        }
    }

    private fun parseOperator0(input: List<Int>): Pair<List<Packet>, Int> {
        var start = 22
        val length = binListToNumber(input.subList(7, start))
        val packets = mutableListOf<Packet>()
        while (start < 22 + length) {
            toPacket(input.subList(start, input.size)).let {
                packets.add(it.first)
                start += it.second
            }
        }
        return Pair(packets.toList(), start)
    }

    private fun parseOperator1(input: List<Int>): Pair<List<Packet>, Int> {
        var start = 18
        val length = binListToNumber(input.subList(7, start))
        val packets = mutableListOf<Packet>()
        for (i in 1..length) {
            toPacket(input.subList(start, input.size)).let {
                packets.add(it.first)
                start += it.second
            }
        }
        return Pair(packets.toList(), start)
    }

    private fun parseLiteral(input: List<Int>, version: Int): Pair<Literal, Int> {
        val bits = mutableListOf<Int>()
        var i = 6
        do {
            bits.addAll(input.subList(i + 1, i + 5)).also { i += 5 }
        } while (input[i - 5] == 1)
        return Pair(Literal(binListToNumber(bits), version), i)
    }

    private fun toBinStr(input: String): String =
        input.toCharArray().joinToString("") {
            it.digitToInt(16).toString(2).padStart(4, '0')
        }

    private fun parse(input: String): Packet = toPacket(toBinStr(input).map { it.digitToInt() }).first

    private fun part1(packet: Packet): Int = sumOfVersions(packet)

    private fun part2(packet: Packet): Long = valueOf(packet)

    operator fun invoke() {
        val input = givenAdventInputFromFile("day16.txt")

        val p = parse(input)
        println("Part 1:")
        println(part1(p))
        println("Part 2:")
        println(part2(p))
    }
}

fun main() {
    Day16()
}
