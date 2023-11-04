package level13

import level09.permutations
import java.io.File

data class Person(val name: String, val mapping: Map<String, Int>)

fun parse(input: List<String>): Map<String, Person> {
    val mapping = mutableMapOf<String, Person>()

    input.forEach { line ->
        val split = line.split(" ")
        val name = split[0]
        val happiness = split[3].toInt()
        val otherGuest = split[10].split(".")[0]
        val isGain = split[2] == "gain"
        val person = mapping.getOrPut(name) { Person(name, emptyMap()) }
        mapping[name] = person.copy(
            mapping = person.mapping.toMutableMap().apply {
                put(otherGuest, if (isGain) happiness else -happiness)
            }
        )
    }

    return mapping
}

fun level13(guests: Map<String, Person>): Int =
    guests.keys.toList().permutations().maxOf { arrangement ->
        arrangement.withIndex().sumOf {
            val guest = guests[it.value] ?: error("")
            val left =
                arrangement[(it.index - 1 + arrangement.size) % arrangement.size]
            val right = arrangement[(it.index + 1) % arrangement.size]

            guest.mapping.getOrDefault(left, 0) +
                    guest.mapping.getOrDefault(right, 0)
        }
    }

fun main() {
    val mapping = parse(File("data/level13/input.txt").readLines())

    println("part1: ${level13(mapping)}")

    val newMapping = mapping.toMutableMap().apply {
        put("Myself", Person("Myself", emptyMap()))
    }
    println("part2: ${level13(newMapping)}")
}
