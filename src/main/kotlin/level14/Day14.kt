package level14

import java.io.File

data class Reindeer(
    val name: String,
    val flightSpeed: Int,
    val flightTime: Int,
    val restTime: Int
) {
    val totalTime = flightTime + restTime
}

fun parse(input: List<String>): List<Reindeer> = input
    .map { line ->
        val splitted = line.split(" ")
        Reindeer(
            name = splitted[0],
            flightSpeed = splitted[3].toInt(),
            flightTime = splitted[6].toInt(),
            restTime = splitted[13].toInt()
        )
    }

fun winnerAtTime(input: List<Reindeer>, t: Int): List<Pair<Reindeer, Int>> {
    val sorted = input.map { reindeer ->
        var distance =
            (t / reindeer.totalTime) * reindeer.flightSpeed * reindeer.flightTime
        val remaining = (t % reindeer.totalTime)
        distance +=
            if (remaining < reindeer.flightTime) remaining * reindeer.flightSpeed
            else reindeer.flightSpeed * reindeer.flightTime
        Pair(reindeer, distance)
    }.sortedByDescending { it.second }

    return sorted.takeWhile { it.second == sorted.first().second }
}

fun level14part1(input: List<Reindeer>, t: Int): Int =
    winnerAtTime(input, t).first().second

fun level14part2(input: List<Reindeer>, t: Int): Int {
    val winTable = mutableMapOf<Reindeer, Int>()
    (1..t).forEach {
        winnerAtTime(input, it).forEach { (winner, _) ->
            winTable[winner] = winTable.getOrDefault(winner, 0) + 1
        }
    }

    return winTable.values.max()
}

fun main() {
    val parsed = parse(File("data/level14/input.txt").readLines())

    println("part1: ${level14part1(parsed, 2503)}")
    println("part2: ${level14part2(parsed, 2503)}")
}