package level21

import level09.permutations
import kotlin.math.max

data class Player(
    val damage: Int,
    val armor: Int
) : Comparable<Player> {
    override fun compareTo(other: Player): Int =
        max(damage - other.armor, 1).compareTo(max(other.damage - armor, 1))
}

data class Item(val price: Int, val damage: Int, val armor: Int)

val WEAPONS = listOf(
    Item(8, 4, 0),
    Item(10, 5, 0),
    Item(25, 6, 0),
    Item(40, 7, 0),
    Item(74, 8, 0)
)

val ARMORS = listOf(
    null,
    Item(13, 0, 1),
    Item(31, 0, 2),
    Item(53, 0, 3),
    Item(75, 0, 4),
    Item(102, 0, 5)
)

val RINGS = listOf(
    null,
    null,
    Item(25, 1, 0),
    Item(50, 2, 0),
    Item(100, 3, 0),
    Item(20, 0, 1),
    Item(40, 0, 2),
    Item(80, 0, 3)
)

fun level21(boss: Player, win: Boolean, min: Boolean): Int {
    val results = WEAPONS.flatMap { weapon ->
        ARMORS.flatMap { armor ->
            RINGS.permutations().map { it.take(2) }
                .toSet()
                .map {
                    Triple(weapon, armor, it)
                }
        }
    }.filter { t ->
        val player = Player(
            damage = t.first.damage +
                    (t.second?.damage ?: 0) +
                    (t.third.sumBy { it?.damage ?: 0 }),
            armor = (t.second?.armor ?: 0) +
                    (t.third.sumBy { it?.armor ?: 0 })
        )
        if (win) player >= boss
        else player < boss
    }.map {
        it.first.price +
                (it.second?.price ?: 0) +
                it.third.sumBy { v -> v?.price ?: 0 }
    }
    return if (min) results.min()!! else results.max()!!
}

fun main() {
    val boss = Player(damage = 8, armor = 2)
    println("part1 ${level21(boss, win = true, min = true)}")
    println("part1 ${level21(boss, win = false, min = false)}")
}