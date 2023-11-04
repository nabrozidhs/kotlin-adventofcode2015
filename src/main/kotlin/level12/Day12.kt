package level12

import kotlinx.serialization.json.*
import java.io.File

fun parse(input: String): JsonElement = Json.parseToJsonElement(input)

fun recursiveCount(json: JsonElement, ignoreRed: Boolean): Long =
    when (json) {
        is JsonPrimitive -> json.longOrNull ?: 0L
        is JsonArray -> json.sumOf { recursiveCount(it, ignoreRed) }
        is JsonObject ->
            if (ignoreRed && json.values.contains(JsonPrimitive("red"))) 0
            else json.values.sumOf { recursiveCount(it, ignoreRed) }

        else -> throw IllegalArgumentException()
    }

fun level12(json: JsonElement, ignoreRed: Boolean): Long =
    recursiveCount(json, ignoreRed)

fun main() {
    val json = parse(File("data/level12/input.txt").readText())

    println("part1: ${level12(json, false)}")
    println("part2: ${level12(json, true)}")
}