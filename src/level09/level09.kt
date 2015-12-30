package level09

import java.io.File

fun <T> List<T>.permutations() : List<List<T>> {
    if (size <= 1) {
        return listOf(this)
    } else {
        val ps : MutableList<List<T>> = arrayListOf()

        for (i in 0..(size - 1)) {
            ps.addAll((subList(0, i) + subList(i + 1, size))
                    .permutations()
                    .map { listOf(this[i]) + it })
        }

        return ps
    }
}

fun parseMap(s : String) : List<Int> {
    val map : MutableMap<Set<String>, Int> = hashMapOf()

    s.split("\n").forEach {
        val line = it.split(" ")
        val key = setOf(line[0], line[2])
        val value = line[4].toInt()

        map.put(key, value)
    }

    return map.keys.flatMap { it }.toSet().toList().permutations()
            .map {
                var sum = 0
                for (i in 0..(it.size - 2)) {
                    sum += map.get(setOf(it[i], it[i + 1]))!!
                }
                sum
            }
}

fun level09(s : String) : Int = parseMap(s).min()!!

fun level09b(s : String) : Int = parseMap(s).max()!!

fun main(args : Array<String>) {
    println(level09(File("data/level09/input.txt").readText()))
    println(level09b(File("data/level09/input.txt").readText()))
}
