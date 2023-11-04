package level08

import java.io.File

fun level08(s : String) : Int =
    s.split("\n").sumOf {
        it.length -
                it.substring(1, it.length - 1)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\")
                    .replace(Regex("""\\x[0-9a-f]{2}"""), "d")
                    .length
    }

fun level08b(s : String) : Int =
    s.split("\n").sumOf {
        2 + it.count { it == '"' || it == '\\' }
    }

fun main() {
    println(level08(File("data/level08/input.txt").readText()))
    println(level08b(File("data/level08/input.txt").readText()))
}
