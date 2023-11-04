package level10

fun level10(s: String): String {
    val sb = StringBuilder()
    var count = 1
    for (i in 0..(s.length - 2)) {
        if (s[i] == s[i + 1]) {
            count++
        } else {
            sb.append(count)
            sb.append(s[i])
            count = 1
        }
    }
    sb.append(count)
    sb.append(s[s.length - 1])
    return sb.toString()
}

fun main() {
    println((0..39).fold("3113322113") { acc, _ -> level10(acc) }.length)
    println((0..49).fold("3113322113") { acc, _ -> level10(acc) }.length)
}
