package level18

import java.io.File

typealias Position = Pair<Int, Int>

data class Map(val width: Int, val height: Int, val walls: Set<Position>)

class State(initial: Map, private val keepCornersOn: Boolean) {

    private var current: Map = initial

    init {
        if (keepCornersOn) {
            val walls = initial.walls.toMutableSet()
            walls.add(Pair(0, 0))
            walls.add(Pair(current.width - 1, 0))
            walls.add(Pair(0, current.height - 1))
            walls.add(Pair(current.width - 1, current.height - 1))
            current = current.copy(walls = walls)
        }
    }

    fun next() {
        val walls = mutableSetOf<Position>()
        (0 until current.height).map { y ->
            (0 until current.width).map { x ->
                val pos = Pair(x, y)
                val containsCurrentPosition = current.walls.contains(pos)
                val matches = pos.adjacentPositions()
                    .count { current.walls.contains(it) }
                if ((containsCurrentPosition && (2..3).contains(matches)) ||
                    (!containsCurrentPosition && matches == 3)
                ) {
                    walls.add(pos)
                }
            }
        }
        if (keepCornersOn) {
            walls.add(Pair(0, 0))
            walls.add(Pair(current.width - 1, 0))
            walls.add(Pair(0, current.height - 1))
            walls.add(Pair(current.width - 1, current.height - 1))
        }
        current = current.copy(walls = walls)
    }

    fun lightsOn(): Int = current.walls.size

    private fun Position.adjacentPositions(): List<Position> = listOf(
        Pair(first - 1, second - 1),
        Pair(first, second - 1),
        Pair(first + 1, second - 1),
        Pair(first - 1, second),
        Pair(first + 1, second),
        Pair(first - 1, second + 1),
        Pair(first, second + 1),
        Pair(first + 1, second + 1)
    ).filter {
        (0 until current.width).contains(it.first) &&
                (0 until current.height).contains(it.second)
    }
}

fun parse(input: String): Map {
    val lines = input.lines()
    val width = lines[0].length
    val height = lines.size
    val walls = mutableSetOf<Position>()

    for ((y, row) in lines.withIndex()) {
        for ((x, c) in row.withIndex()) {
            if (c == '#') {
                walls.add(Pair(x, y))
            }
        }
    }
    return Map(width = width, height = height, walls = walls)
}

fun level18(map: Map, steps: Int, keepCornersOn: Boolean): Int {
    val state = State(map, keepCornersOn)

    (1..steps).forEach { _ -> state.next() }

    return state.lightsOn()
}

fun main() {
    val map = parse(File("data/level18/input.txt").readText())
    println("part1: ${level18(map, 100, false)}")
    println("part2: ${level18(map, 100, true)}")
}