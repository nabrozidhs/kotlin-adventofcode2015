package level01

import java.io.File

fun level01(s : String) : Int =
    s.toCharArray().fold(0) { n, c -> n + (if (c == '(') 1 else -1) }

fun level01b(s : String) : Int {
    var count = 0
    for (c in s.toCharArray().withIndex()) {
        when (c.value) {
            '(' -> count++
            ')' -> {
                count--
                if (count < 0) {
                    return c.index + 1
                }
            }
        }
    }
    return -1
}

fun main() {
    val content = File("data/level01/input.txt").readText()
    println(level01(content))
    println(level01b(content))
}
