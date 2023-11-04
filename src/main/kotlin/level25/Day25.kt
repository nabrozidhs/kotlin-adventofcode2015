package level25

fun main() {
    var code = 20151125L
    var row = 1
    var col = 1
    while (row != 2981 || col != 3075) {
        if (row - 1 < 1) {
            row = col + 1
            col = 1
        } else {
            row -= 1
            col += 1
        }
        code = (code * 252533) % 33554393
    }
    println(code)
}