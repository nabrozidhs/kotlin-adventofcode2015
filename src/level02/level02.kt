package level02

import java.io.File

data class GiftBox(val length : Int,
                   val width : Int,
                   val height : Int)

fun GiftBox.surface() : Int =
    2 * length * width +
    2 * width * height +
    2 * height * length

fun GiftBox.smallestSideArea() : Int =
    listOf(length * width, width * height, height * length).min()!!

fun parseString(s : String) : GiftBox {
    val a = s.split("x").map { it.toInt() }
    return GiftBox(a[0], a[1], a[2])
}

fun level02(s : String) : Int =
    s.split("\n").map { parseString(it) }.map { it.surface() + it.smallestSideArea() }.sum()

fun main(args : Array<String>) {
    println(level02(File("data/level02/input.txt").readText()))
}
