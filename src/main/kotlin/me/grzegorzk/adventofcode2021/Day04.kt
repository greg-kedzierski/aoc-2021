package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day04 {
    data class Cell(val value: Int, var marked: Boolean = false)

    private fun prepareBingoInput(input: String) =
        input.split("\r\n\r\n").let { lines ->
            Pair(
                lines[0].split(",").map { Integer.parseInt(it) },
                lines.drop(1).map {
                    it.split("\r\n").map { row ->
                        row.trim().split("\\s+".toRegex())
                            .map { value -> Cell(Integer.parseInt(value)) }
                    }
                })
        }


    private fun playBingo(numbersToDraw: List<Int>, boards: List<List<List<Cell>>>): Map<Int, Pair<Int, Int>> {
        val results = mutableMapOf<Int, Pair<Int, Int>>()

        numbersToDraw.forEachIndexed { index, draw ->
            boards.forEachIndexed { boardIndex, board ->
                if (!results.containsKey(boardIndex)) {
                    for (row in board) {
                        row.forEachIndexed { colIndex, ceil ->
                            if (ceil.value == draw) {
                                ceil.marked = true
                            }
                            val bingo = allMarkedInRow(row) || allMarkedInColumn(board, colIndex)
                            if (bingo) {
                                results.putIfAbsent(boardIndex, Pair(sumOfUnmarkedCells(board) * draw, index))
                            }
                        }
                    }
                }
            }
        }

        return results
    }

    private fun allMarkedInColumn(board: List<List<Cell>>, colIndex: Int) =
        board.fold(true) { acc, r ->
            acc && r[colIndex].marked
        }

    private fun allMarkedInRow(row: List<Cell>) =
        row.fold(true) { acc, ceil ->
            acc && ceil.marked
        }

    private fun sumOfUnmarkedCells(board: List<List<Cell>>) =
        board.flatten().filter { !it.marked }.map { it.value }.fold(0, Integer::sum)

    private fun bingo(input: String): Pair<Int, Int> {
        val (numbersToDraw, boards) = prepareBingoInput(input)

        val boardResult = playBingo(numbersToDraw, boards)

        return boardResult.values.let {
            Pair(
                it.reduce { a, b -> if (a.second < b.second) a else b }.first,
                it.reduce { a, b -> if (a.second > b.second) a else b }.first
            )
        }
    }

    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day04_sample.txt")
        val input = givenAdventInputFromFile("day04.txt")

        val b1 = bingo(sampleInput)
        val b2 = bingo(input)

        println("Part 1:")
        println(b1.first)
        println(b2.first)
        println("Part 2:")
        println(b1.second)
        println(b2.second)
    }
}

fun main() {
    Day04()
}
