package level03

import java.io.File

fun level03(s : String) : Int {
    var startPosition = Pair(0, 0)
    var housesVisited = hashSetOf<Pair<Int, Int>>()
    for (c in s.toCharArray()) {
        housesVisited.add(startPosition)
        startPosition = when (c) {
            '^' -> Pair(startPosition.first, startPosition.second + 1)
            'v' -> Pair(startPosition.first, startPosition.second - 1)
            '<' -> Pair(startPosition.first - 1, startPosition.second)
            '>' -> Pair(startPosition.first + 1, startPosition.second)
            else -> throw RuntimeException()
        }
    }

    return housesVisited.size
}

fun main(args : Array<String>) {
    println(level03(File("data/level03/input.txt").readText()))
}
