package level07

import java.io.File
import java.util.*
import kotlin.text.Regex

val PATTERN_NUMBER = Regex("\\d+")
val PATTERN_OP = Regex("(.+) -> (.+)")

enum class Operation {
    NOT,
    AND,
    OR,
    LSHIFT,
    RSHIFT
}

abstract class Expr
class Num(val value : Int) : Expr()
class Var(val value : String) : Expr()
class Op(val op : Operation, vararg val args : Expr) : Expr()

class Evaluator(val program: Map<String, Expr>) {
    private val cache : MutableMap<Expr, Int> = hashMapOf();

    fun eval(expr : Expr) : Int {
        val cachedValue = cache[expr]
        if (cachedValue == null) {
            val value = when (expr) {
                is Var -> {
                    if (PATTERN_NUMBER.matches(expr.value)) {
                        expr.value.toInt()
                    } else {
                        eval(program[expr.value]!!)
                    }
                }
                is Num -> expr.value
                is Op -> when (expr.op) {
                    Operation.NOT -> eval(expr.args[0]).inv() and 0xFFFF
                    Operation.AND -> eval(expr.args[0]) and eval(expr.args[1])
                    Operation.OR -> eval(expr.args[0]) or eval(expr.args[1])
                    Operation.RSHIFT -> eval(expr.args[0]) ushr eval(expr.args[1])
                    Operation.LSHIFT -> (eval(expr.args[0]) shl eval(expr.args[1])) and 0xFFFF
                    else -> throw RuntimeException()
                }
                else -> throw RuntimeException()
            }
            cache[expr] = value
            return value
        } else {
            return cachedValue
        }
    }
}

fun buildExpr(s : String) : Expr {
    val tokens = s.split(" ")
    return when (tokens.size) {
        1 -> Var(tokens[0])
        2 -> Op(Operation.NOT, Var(tokens[1]))
        3 -> when (tokens[1]) {
            "AND" -> Op(Operation.AND, Var(tokens[0]), Var(tokens[2]))
            "OR" -> Op(Operation.OR, Var(tokens[0]), Var(tokens[2]))
            "RSHIFT" -> Op(Operation.RSHIFT, Var(tokens[0]), Num(tokens[2].toInt()))
            "LSHIFT" -> Op(Operation.LSHIFT, Var(tokens[0]), Num(tokens[2].toInt()))
            else -> throw RuntimeException()
        }
        else -> throw RuntimeException()
    }
}

fun level07(s : String) : Map<String, Expr> {
    val map: HashMap<String, Expr> = hashMapOf()

    s.split("\n").forEach {
        val groups = PATTERN_OP.matchEntire(it)!!.groups
        map[groups[2]!!.value] = buildExpr(groups[1]!!.value)
    }

    return map
}

fun level07b(s : String) : Map<String, Expr> = level07(s + "\n3176 -> b")

fun main(args : Array<String>) {
    val map1 = level07(File("data/level07/input.txt").readText())
    println(Evaluator(map1).eval(map1["a"]!!))
    val map2 = level07b(File("data/level07/input.txt").readText())
    println(Evaluator(map2).eval(map2["a"]!!))
}
