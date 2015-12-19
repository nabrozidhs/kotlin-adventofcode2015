package level01

import java.io.File

fun level01(s : String) : Int =
    s.toCharArray().fold(0, { n, c -> n + (if (c == '(') 1 else -1) })

fun main(args : Array<String>) {
    println(level01(File("data/level01/input.txt").readText()))
}
