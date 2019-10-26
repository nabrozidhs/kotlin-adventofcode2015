package level16

import java.io.File

data class Aunt(
    val id: Int,
    val stuff: Map<String, Int>
)

val pattern = Regex("Sue (\\d+): (.+)")
fun parse(input: List<String>): List<Aunt> = input
    .map { line ->
        val match = pattern.matchEntire(line)!!
        Aunt(
            id = match.groupValues[1].toInt(),
            stuff = mutableMapOf<String, Int>().apply {
                match.groupValues[2].split(",").forEach {
                    val s = it.split(':')
                    this[s[0].trim()] = s[1].trim().toInt()
                }
            }
        )
    }

fun level16part1(input: List<Aunt>, conditions: Map<String, Int>): Int = input
    .map { aunt ->
        Pair(
            aunt,
            conditions.count { (k, v) -> aunt.stuff.getOrDefault(k, -1) == v }
        )
    }.maxBy { it.second }!!
    .first
    .id

fun level16part2(input: List<Aunt>, conditions: Map<String, Int>): Int = input
    .map { aunt ->
        Pair(
            aunt,
            conditions.count { (k, v) ->
                val amount = aunt.stuff[k] ?: return@count false
                when (k) {
                    "cats", "trees" -> amount > v
                    "pomeranians", "goldfish" -> amount < v
                    else -> amount == v
                }
            }
        )
    }.maxBy { it.second }!!
    .first
    .id

fun main() {
    val parsed = parse(File("data/level16/input.txt").readLines())

    val conditions = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )
    println("part1: ${level16part1(parsed, conditions)}")
    println("part2: ${level16part2(parsed, conditions)}")
}