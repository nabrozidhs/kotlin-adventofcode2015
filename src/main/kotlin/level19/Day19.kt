package level19

import java.io.File

data class Input(
    val mapping: Map<String, List<String>>,
    val input: String
)

fun parse(input: List<String>): Input {
    val mapping = mutableMapOf<String, List<String>>()
    var nextLineIsInput = false

    for (line in input) {
        when {
            nextLineIsInput -> return Input(mapping, line)
            line.isBlank() -> nextLineIsInput = true
            else -> {
                val splitted = line.split(" => ")
                val key = splitted[0]
                mapping[key] = mapping.getOrDefault(key, emptyList())
                    .toMutableList()
                    .apply {
                        add(splitted[1])
                    }
            }
        }
    }

    throw IllegalArgumentException()
}

fun day19Part1(mapping: Map<String, List<String>>, input: String): Int {
    val generated = mutableSetOf<String>()
    for ((toMatch, replacements) in mapping) {
        for (replacement in replacements) {
            if (!input.contains(toMatch)) {
                break
            }
            var index = 0
            while (index < input.length) {
                index +=
                    if (input.subSequence(index, input.length)
                            .startsWith(toMatch)
                    ) {
                        generated.add(
                            input.substring(0, index) +
                                    replacement +
                                    input.substring(
                                        index + toMatch.length,
                                        input.length
                                    )
                        )
                        toMatch.length
                    } else 1
            }
        }
    }

    return generated.size
}

fun day19Part2(mapping: Map<String, List<String>>, input: String): Int {
    var generations = 0
    var temp = input
    val searchKeys = mapping.flatMap { it.value.map { v -> Pair(v, it.key) } }
        .sortedByDescending { it.first.length }
    while (temp != "e") {
        for ((replacement, toMatch) in searchKeys) {
            val index = temp.indexOf(replacement)
            if (index < 0) {
                continue
            }

            temp = temp.substring(0, index) +
                    toMatch +
                    if (index + replacement.length >= temp.length) ""
                    else {
                        temp.substring(
                            index + replacement.length,
                            temp.length
                        )
                    }
            break
        }
        generations += 1
    }
    return generations
}

fun main() {
    val input = parse(File("data/level19/input.txt").readLines())

    println("part1: ${day19Part1(input.mapping, input.input)}")
    println("part2: ${day19Part2(input.mapping, input.input)}")
}