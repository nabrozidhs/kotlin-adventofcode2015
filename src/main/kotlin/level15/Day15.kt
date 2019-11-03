package level15

import java.io.File
import kotlin.math.max

data class Ingredient(
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

fun parse(input: List<String>): List<Ingredient> = input.map {
    val splitted = it.split(" ")
    Ingredient(
        splitted[2].split(",")[0].toInt(),
        splitted[4].split(",")[0].toInt(),
        splitted[6].split(",")[0].toInt(),
        splitted[8].split(",")[0].toInt(),
        splitted[10].split(",")[0].toInt()
    )
}

fun recursive(ingredients: List<Ingredient>, remaining: Int): List<List<Int>> {
    if (ingredients.size == 1) {
        return listOf(listOf(remaining))
    }
    if (remaining == 0) {
        return recursive(ingredients.drop(1), remaining).map { listOf(0) + it }
    }

    return (0..remaining).map { i ->
        recursive(ingredients.drop(1), remaining - i).map { listOf(i) + it }
    }.flatten()
}

fun day15(ingredients: List<Ingredient>, filterCalories: Boolean): Int {
    return recursive(ingredients, 100)
        .map {
            val capacity = max(0, ingredients.zip(it)
                .sumBy { (ingredient, i) -> ingredient.capacity * i })
            val durability = max(0, ingredients.zip(it)
                .sumBy { (ingredient, i) -> ingredient.durability * i })
            val flavor = max(0, ingredients.zip(it)
                .sumBy { (ingredient, i) -> ingredient.flavor * i })
            val texture = max(0, ingredients.zip(it)
                .sumBy { (ingredient, i) -> ingredient.texture * i })
            val calories = ingredients.zip(it)
                .sumBy { (ingredient, i) -> ingredient.calories * i }
            Pair(capacity * durability * flavor * texture, calories)
        }
        .filter { !filterCalories || it.second == 500 }
        .map { it.first }
        .max()!!
}

fun main() {
    val ingredients = parse(File("data/level15/input.txt").readLines())

    println("part1: ${day15(ingredients, false)}")
    println("part2: ${day15(ingredients, true)}")
}