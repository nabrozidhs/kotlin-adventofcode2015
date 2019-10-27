package level20

import kotlin.math.sqrt

fun divisors(n: Int): List<Int> {
    val divisors = mutableListOf<Int>()

    (1..sqrt(n.toDouble()).toInt()).forEach {
        if (n % it == 0) {
            divisors.add(it)
            val other = n / it
            if (other != it) {
                divisors.add(other)
            }
        }
    }

    return divisors
}

fun presentsForHouse(house: Int, numPresents: Int, limitTo50: Boolean): Int =
    divisors(house)
        .filter { !limitTo50 || house / it <= 50}
        .sumBy { it * numPresents }

fun level20(n: Long, numPresents: Int, limitTo50: Boolean): Int =
    (1..Int.MAX_VALUE).asSequence()
        .first { presentsForHouse(it, numPresents, limitTo50) >= n }

fun main() {
    println("part1 ${level20(33100000, 10, false)}")
    println("part2 ${level20(33100000, 11, true)}")
}