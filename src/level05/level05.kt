package level05

import java.io.File

val VOWELS = setOf('a', 'e', 'i', 'o', 'u')
val NASTY_STRINGS = setOf("ab", "cd", "pq", "xy")

fun containsVowels(s : String) : Boolean =
    s.toCharArray().count { VOWELS.contains(it) } >= 3

fun containsRepeatedChar(s : String) : Boolean {
    var prev : Char = ' '
    for (c in s.toCharArray()) {
        if (c == prev) {
            return true
        }
        prev = c
    }
    return false
}

fun containsNastyString(s : String) : Boolean =
    NASTY_STRINGS.asSequence().filter { s.contains(it) }.any()

fun level05(s : String) : Int =
    s.split("\n").filter {
        containsVowels(it) &&
                containsRepeatedChar(it) &&
                !containsNastyString(it)
    }.count()

fun main(args : Array<String>) {
    println(level05(File("data/level05/input.txt").readText()))
}
