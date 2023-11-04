package level06

import java.io.File
import java.util.regex.Pattern

data class Coord(val x: Int, val y: Int) : Comparable<Coord> {
    override fun compareTo(other: Coord): Int =
        if (y == other.y) x.compareTo(other.x)
        else y.compareTo(other.y)
}

class CoordRange(
    override val start: Coord,
    override val endInclusive: Coord,
) : ClosedRange<Coord>, Iterable<Coord> {
    override fun iterator(): Iterator<Coord> = CoordIterator(this)
}

class CoordIterator(private val coordRange: CoordRange) : Iterator<Coord> {

    private var current = coordRange.start

    override fun next(): Coord {
        val result = current
        current =
            if (current.x + 1 <= coordRange.endInclusive.x) Coord(current.x + 1, current.y)
            else Coord(coordRange.start.x, current.y + 1)
        return result
    }

    override fun hasNext(): Boolean = current <= coordRange.endInclusive
}

operator fun Coord.rangeTo(other: Coord) = CoordRange(this, other)

class Action<T>(val f: (T) -> T) {
    fun perform(map: Array<Array<T>>, coord: Coord) {
        map[coord.x][coord.y] = f(map[coord.x][coord.y])
    }
}

val ACTION_MAP = mapOf(
    "turn on" to Action<Boolean> { true },
    "turn off" to Action { false },
    "toggle" to Action { !it }
)

val PATTERN = Pattern.compile("(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)")!!

val ACTION_MAP2 = mapOf(
    "turn on" to Action<Int> { it + 1 },
    "turn off" to Action { 0.coerceAtLeast(it - 1) },
    "toggle" to Action { it + 2 }
)

fun level06(s: String): Int {
    val map: Array<Array<Boolean>> = Array(1000) { Array(1000) { false } }

    for (line in s.split("\n")) {
        val matcher = PATTERN.matcher(line)
        if (!matcher.matches()) {
            continue
        }

        val action = ACTION_MAP[matcher.group(1)]!!
        val start = Coord(matcher.group(2).toInt(), matcher.group(3).toInt())
        val end = Coord(matcher.group(4).toInt(), matcher.group(5).toInt())

        (start..end).forEach { action.perform(map, it) }
    }

    return map.flatMap { it.asIterable() }.count { it }
}

fun level06b(s: String): Int {
    val map: Array<Array<Int>> = Array(1000) { Array(1000) { 0 } }

    for (line in s.split("\n")) {
        val matcher = PATTERN.matcher(line)
        if (!matcher.matches()) {
            continue
        }

        val action = ACTION_MAP2[matcher.group(1)]!!
        val start = Coord(matcher.group(2).toInt(), matcher.group(3).toInt())
        val end = Coord(matcher.group(4).toInt(), matcher.group(5).toInt())

        (start..end).forEach { action.perform(map, it) }
    }

    return map.flatMap { it.asIterable() }.sum()
}

fun main() {
    println(level06(File("data/level06/input.txt").readText()))
    println(level06b(File("data/level06/input.txt").readText()))
}
