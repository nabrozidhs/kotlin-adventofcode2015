package level02

import java.io.File

data class GiftBox(val length : Int,
                   val width : Int,
                   val height : Int)

fun GiftBox.surface() : Int =
    2 * length * width +
    2 * width * height +
    2 * height * length

fun GiftBox.volume() : Int = length * width * height

fun GiftBox.sidePerimeters() : List<Int> =
    listOf(2 * (length + width),
            2 * (width + height),
            2 * (height + length))

fun GiftBox.sideAreas() : List<Int> =
    listOf(length * width,
            width * height,
            height * length)

fun parseString(s : String) : GiftBox {
    val a = s.split("x").map { it.toInt() }
    return GiftBox(a[0], a[1], a[2])
}

fun parseGiftBoxes(s : String) : List<GiftBox> = s.split("\n").map { parseString(it) }

fun level02(s : String) : Int =
    parseGiftBoxes(s).map { it.surface() + it.sideAreas().min()!! }.sum()

fun level02b(s : String) : Int =
    parseGiftBoxes(s).map { it.volume() + it.sidePerimeters().min()!! }.sum()

fun main(args : Array<String>) {
    val content = File("data/level02/input.txt").readText()
    println(level02(content))
    println(level02b(content))
}
