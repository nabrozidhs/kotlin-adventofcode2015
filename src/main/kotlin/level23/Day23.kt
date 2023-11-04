package level23

import java.io.File

class State(startValueA: Long) {

    val registers = mutableMapOf('a' to startValueA)
    var idx: Int = 0
}

sealed class Command {

    abstract fun run(state: State)

    class Hlf(private val r: Char) : Command() {

        override fun run(state: State) {
            state.registers[r] = state.registers.getOrDefault(r, 0) / 2
        }
    }

    class Tpl(private val r: Char) : Command() {

        override fun run(state: State) {
            state.registers[r] = state.registers.getOrDefault(r, 0) * 3
        }
    }

    class Inc(private val r: Char) : Command() {

        override fun run(state: State) {
            state.registers[r] = state.registers.getOrDefault(r, 0) + 1
        }
    }

    class Jmp(private val offset: Int) : Command() {
        override fun run(state: State) {
            state.idx += offset - 1
        }
    }

    class Jie(private val r: Char, private val offset: Int) : Command() {
        override fun run(state: State) {
            if (state.registers.getOrDefault(r, 0) % 2 == 0L) {
                state.idx += offset - 1
            }
        }
    }

    class Jio(private val r: Char, private val offset: Int) : Command() {
        override fun run(state: State) {
            if (state.registers.getOrDefault(r, 0) == 1L) {
                state.idx += offset - 1
            }
        }
    }
}

fun parse(input: List<String>): List<Command> = input.map { line ->
    val split = line.split(" ")
    when (split[0]) {
        "hlf" -> Command.Hlf(split[1][0])
        "tpl" -> Command.Tpl(split[1][0])
        "inc" -> Command.Inc(split[1][0])
        "jmp" -> Command.Jmp(split[1].toInt())
        "jie" -> Command.Jie(split[1][0], split[2].toInt())
        "jio" -> Command.Jio(split[1][0], split[2].toInt())
        else -> throw IllegalArgumentException()
    }
}

fun level23(program: List<Command>, startValueA: Long): Long {
    val state = State(startValueA)
    while (state.idx < program.size) {
        program[state.idx].run(state)
        state.idx += 1
    }
    return state.registers.getOrDefault('b', 0L)
}

fun main() {
    val program = parse(File("data/level23/input.txt").readLines())
    println("part1 ${level23(program, 0)}")
    println("part2 ${level23(program, 1)}")
}