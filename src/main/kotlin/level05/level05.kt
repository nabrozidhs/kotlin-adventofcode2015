package level05

import java.io.File

val VOWELS = setOf('a', 'e', 'i', 'o', 'u')
val NASTY_STRINGS = setOf("ab", "cd", "pq", "xy")

fun containsVowels(s : String) : Boolean =
    s.toCharArray().count { VOWELS.contains(it) } >= 3

fun containsRepeatedChar(s : String) : Boolean {
    for (i in 0..(s.length - 2)) {
        if (s[i] == s[i + 1]) {
            return true
        }
    }
    return false
}

fun containsNastyString(s : String) : Boolean =
    NASTY_STRINGS.asSequence().filter { s.contains(it) }.any()

fun letterRepeated(s : String) : Boolean {
    for (i in 0..(s.length - 3)) {
        if (s[i] == s[i + 2]) {
            return true
        }
    }
    return false
}

fun repeatedCharTwice(s : String) : Boolean {
    for (i in 0..(s.length - 2)) {
        if (s.substring(i+2).contains(s.substring(i, i + 2))) {
            return true
        }
    }
    return false
}

fun level05(s : String) : Int =
    s.split("\n").filter {
        containsVowels(it) &&
                containsRepeatedChar(it) &&
                !containsNastyString(it)
    }.count()

fun level05b(s : String) : Int =
    s.split("\n").filter {
        letterRepeated(it) &&
                repeatedCharTwice(it)
    }.count()

fun main() {
    println(level05(File("data/level05/input.txt").readText()))
    println(level05b(File("data/level05/input.txt").readText()))
}
