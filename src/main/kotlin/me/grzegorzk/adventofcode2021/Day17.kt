package me.grzegorzk.adventofcode2021

object Day17 {
    private fun parse(input: String): Pair<IntRange, IntRange> =
        """target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)""".toRegex().find(input)!!.destructured
            .let { (x1, x2, y1, y2) ->
                Pair(x1.toInt()..x2.toInt(), y1.toInt()..y2.toInt())
            }

    private fun inRange(x: Int, y: Int, range: Pair<IntRange, IntRange>): Boolean =
        x in range.first && y in range.second

    private fun missedRange(x: Int, y: Int, range: Pair<IntRange, IntRange>): Boolean =
        x > range.first.last || y < range.second.first


    private fun decreaseVelocity(velocity: Pair<Int, Int>): Pair<Int, Int> =
        Pair(
            if (velocity.first > 0) velocity.first - 1 else if (velocity.first < 0) velocity.first + 1 else 0,
            velocity.second - 1
        )

    private fun solve(area: Pair<IntRange, IntRange>): Pair<Int, Int> {
        var maxY = Int.MIN_VALUE
        var counter = 0

        for (velX in 0..area.first.last) {
            for (velY in area.second.first..200) {
                var x = 0
                var y = 0

                var velocity = Pair(velX, velY)
                val successfulMaxY = maxY

                while (true) {
                    x += velocity.first
                    y += velocity.second

                    if (y > maxY) maxY = y

                    if (inRange(x, y, area)) {
                        counter += 1
                        break
                    }
                    if (missedRange(x, y, area)) {
                        maxY = successfulMaxY
                        break
                    }

                    velocity = decreaseVelocity(velocity)
                }
            }
        }

        return Pair(maxY, counter)
    }

    private fun parseAndSolve(input: String): Pair<Int, Int> =
        solve(parse(input))

    operator fun invoke() {
        val sampleInput = "target area: x=20..30, y=-10..-5"
        val input = "target area: x=124..174, y=-123..-86"

        val (sampleMaxY, sampleCounter) = parseAndSolve(sampleInput)
        val (maxY, counter) = parseAndSolve(input)

        println("Part 1:")
        println(sampleMaxY)
        println(maxY)
        println("Part 2:")
        println(sampleCounter)
        println(counter)
    }
}

fun main() {
    Day17()
}
