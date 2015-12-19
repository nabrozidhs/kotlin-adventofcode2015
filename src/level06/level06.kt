package level06

import java.io.File
import java.util.regex.Pattern

data class Coord(val x : Int, val y : Int) : Comparable<Coord> {
    override fun compareTo(other: Coord): Int =
        if (y == other.y) Integer.compare(x, other.x)
        else Integer.compare(y, other.y)
}

class CoordRange(public override val start: Coord,
                 public override val endInclusive: Coord) : ClosedRange<Coord>, Iterable<Coord> {
    override fun iterator(): Iterator<Coord> = CoordIterator(this)
}

class CoordIterator(val coordRange : CoordRange) : Iterator<Coord> {

    var current = coordRange.start

    override fun next(): Coord {
        val result = current
        current =
                if (current.x + 1 <= coordRange.endInclusive.x) Coord(current.x + 1, current.y)
                else Coord(coordRange.start.x, current.y + 1)
        return result
    }

    override fun hasNext(): Boolean = current <= coordRange.endInclusive
}
operator fun Coord.rangeTo(other : Coord) = CoordRange(this, other)

abstract class Action {
    abstract fun perform(map : Array<Array<Boolean>>, coord : Coord)
}
class TurnOn : Action() {
    override fun perform(map: Array<Array<Boolean>>, coord : Coord) {
        map[coord.x][coord.y] = true
    }
}
class TurnOff : Action() {
    override fun perform(map: Array<Array<Boolean>>, coord : Coord) {
        map[coord.x][coord.y] = false
    }
}
class Toggle : Action() {
    override fun perform(map: Array<Array<Boolean>>, coord : Coord) {
        map[coord.x][coord.y] = !map[coord.x][coord.y]
    }
}

val ACTION_MAP = mapOf(
        "turn on" to TurnOn(),
        "turn off" to TurnOff(),
        "toggle" to Toggle())

val PATTERN = Pattern.compile("(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)")

fun level06(s : String) : Int {
    val map: Array<Array<Boolean>> = Array(1000, { Array(1000, { false }) })

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

fun main(args : Array<String>) {
    println(level06(File("data/level06/input.txt").readText()))
}
