package level11

import kotlin.text.Regex

val PATTERN_INVALID = Regex(".*[oil].*")
val PATTERN_REPEATED = Regex(".*(.)\\1.*(.)\\2.*")

fun increasingOrder(s: String): Boolean {
    for (i in 0..(s.length - 3)) {
        if (s[i] + 1 == s[i + 1] &&
                s[i + 1] + 1 == s[i + 2]) {
            return true
        }
    }
    return false
}

fun valid(s: String): Boolean {
    return !PATTERN_INVALID.matches(s) &&
            increasingOrder(s) &&
            PATTERN_REPEATED.matches(s)
}

fun nextString(s: String): String {
    val sb = StringBuilder()
    var acc = 1
    for (c in s.toCharArray().reversed()) {
        if (acc == 1) {
            if (c == 'z') {
                sb.insert(0, 'a')
            } else {
                acc = 0
                sb.insert(0, c + 1)
            }
        } else {
            sb.insert(0, c)
        }
    }

    return sb.toString()
}

fun level11(s: String): String {
    var next = nextString(s)
    while (!valid(next)) {
        next = nextString(next)
    }
    return next
}

fun main(args : Array<String>) {
    val firstPassword = level11("vzbxkghb")
    println(firstPassword)
    println(level11(firstPassword))
}
