package me.grzegorzk.adventofcode2021

import me.grzegorzk.adventofcode2021.utils.givenAdventInputFromFile

object Day20 {

    private fun parse(input: List<String>): Pair<BooleanArray, Array<BooleanArray>> {
        val imageEnhancement = input[0].map { it != '.' }.toBooleanArray()
        val increaseBy = 50 // ✨✨✨ magic number ✨✨✨
        val imageString = input.drop(2)
        val image = Array(imageString.size + (increaseBy shl 1)) {
            BooleanArray(imageString[0].length + (increaseBy shl 1))
        }
        imageString.forEachIndexed() { i, row ->
            row.forEachIndexed { j, char -> image[i + increaseBy][j + increaseBy] = (char != '.') }
        }
        return Pair(imageEnhancement, image)
    }

    private fun enhancementIdx(j: Int, i: Int, xRange: IntRange, yRange: IntRange, image: Array<BooleanArray>) =
        adjacentLocs(j, i)
            .map { (x, y) -> if (x !in xRange || y !in yRange) image[0][0] else image[x][y] }
            .toNumber()

    private fun adjacentLocs(x: Int, y: Int): List<Point> =
        (-1..1).flatMap { i ->
            (-1..1).map { j -> Point(i + x, j + y) }
        }

    private fun List<Boolean>.toNumber(): Int = fold(0) { acc, bit -> (acc shl 1) + if (bit) 1 else 0 }

    private fun enhancementAlg(imageEnhancement: BooleanArray, image: Array<BooleanArray>): Array<BooleanArray> {
        val newImage = Array(image.size) { BooleanArray(image[0].size) }
        val xRange = image[0].indices
        val yRange = image.indices

        for (i in yRange) {
            for (j in xRange) {
                newImage[j][i] = imageEnhancement[enhancementIdx(j, i, xRange, yRange, image)]
            }
        }
        return newImage
    }

    private fun enhancementAlg(imageEnhancement: BooleanArray, image: Array<BooleanArray>, times: Int): Int =
        (1..times).fold(image) { acc, _ ->
            enhancementAlg(imageEnhancement, acc)
        }.sumOf { l -> l.count { it } }

    operator fun invoke() {
        val sampleInput = givenAdventInputFromFile("day20_sample.txt")
        val input = givenAdventInputFromFile("day20.txt")


        val (imageEnhancement, image) = parse(input.lines())
        val (sampleImageEnhancement, sampleImage) = parse(sampleInput.lines())

        println("Part 1:")
        println(enhancementAlg(sampleImageEnhancement, sampleImage, times = 2))
        println(enhancementAlg(imageEnhancement, image, times = 2))
        println("Part 2:")
        println(enhancementAlg(sampleImageEnhancement, sampleImage, times = 50))
        println(enhancementAlg(imageEnhancement, image, times = 50))
    }
}

fun main() {
    Day20()
}
