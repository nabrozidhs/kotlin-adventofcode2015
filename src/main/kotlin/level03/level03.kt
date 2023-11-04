package level03

import java.io.File

fun updatePosition(pos: Pair<Int, Int>, c : Char) : Pair<Int, Int> =
        when (c) {
            '^' -> Pair(pos.first, pos.second + 1)
            'v' -> Pair(pos.first, pos.second - 1)
            '<' -> Pair(pos.first - 1, pos.second)
            '>' -> Pair(pos.first + 1, pos.second)
            else -> throw RuntimeException()
        }

fun level03(s : String) : Int {
    var startPosition = Pair(0, 0)
    val housesVisited = hashSetOf<Pair<Int, Int>>()
    for (c in s.toCharArray()) {
        housesVisited.add(startPosition)
        startPosition = updatePosition(startPosition, c)
    }

    return housesVisited.size
}

fun level03b(s : String) : Int {
    var santaPosition = Pair(0, 0)
    var robotPosition = Pair(0, 0)
    val housesVisited = hashSetOf<Pair<Int, Int>>()
    for (c in s.toCharArray().withIndex()) {
        if (c.index % 2 == 0) {
            housesVisited.add(santaPosition)
            santaPosition = updatePosition(santaPosition, c.value)
        } else {
            housesVisited.add(robotPosition)
            robotPosition = updatePosition(robotPosition, c.value)
        }
    }

    return housesVisited.size
}

fun main() {
    println(level03(File("data/level03/input.txt").readText()))
    println(level03b(File("data/level03/input.txt").readText()))
}
