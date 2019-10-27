package level17

fun recursive(
    current: List<Int>,
    remaining: List<Int>,
    target: Int
): List<List<Int>> {
    if (target == 0) {
        return listOf(current)
    }
    if (remaining.isEmpty() || target < 0) {
        return emptyList()
    }

    return remaining.indices
        .map {
            recursive(
                current + remaining[it],
                remaining.drop(it + 1),
                target - remaining[it]
            )
        }
        .filter { it.isNotEmpty() }
        .flatten()
}

fun level17part1(input: List<Int>, target: Int): Int =
    recursive(emptyList(), input, target).size

fun level17part2(input: List<Int>, target: Int): Int {
    val results = recursive(emptyList(), input, target).sortedBy { it.size }

    return results.takeWhile { it.size == results.first().size }.size
}

fun main() {
    val input = listOf(
        33, 14, 18, 20, 45, 35, 16, 35, 1,
        13, 18, 13, 50, 44, 48, 6, 24, 41, 30, 42
    ).sorted()
    println("part1: ${level17part1(input, 150)}")
    println("part2: ${level17part2(input, 150)}")
}