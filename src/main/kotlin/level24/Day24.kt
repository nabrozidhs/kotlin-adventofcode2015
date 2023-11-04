package level24

import java.io.File
import java.lang.Long.parseLong

fun findSequencesThatSumTo(input: List<Long>, sum: Long): List<Set<Long>>? {
    if (input.isEmpty()) {
        return null
    }

    return input.mapIndexedNotNull { index, value ->
        if (value > sum) {
            return@mapIndexedNotNull null
        } else if (value == sum) {
            listOf(setOf(value))
        } else {
            val rest = input.drop(index + 1)
            val newSum = sum - value
            if (rest.sum() < newSum) {
                return@mapIndexedNotNull null
            }

            findSequencesThatSumTo(rest, newSum)?.map { setOf(value) + it }
        }
    }.flatten()
}

fun level24(input: Set<Long>, numberOfGroups: Int): Long {
    val sequences = findSequencesThatSumTo(input.sortedDescending(), input.sum() / numberOfGroups)!!
        .groupBy { it.size }

    return sequences.entries.sortedBy { it.key }
        .asSequence()
        .mapNotNull { (_, value) -> value.minOfOrNull { it.fold(1L) { acc, e -> acc * e } } }
        .first()
}

fun main() {
    val input = File("data/level24/input").readLines().map(::parseLong).toSet()
    println("part1 ${level24(input, 3)}")
    println("part2 ${level24(input, 4)}")
}