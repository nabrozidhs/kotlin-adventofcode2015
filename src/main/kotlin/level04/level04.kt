package level04

import java.security.MessageDigest

fun level04(s : String) : Int {
    val md5 = MessageDigest.getInstance("MD5")
    var i = 0
    while (true) {
        val res = md5.digest((s + i).toByteArray())
        if (res[0] == (0).toByte() && res[1] == (0).toByte() && (res[2].toInt() shr 4) == 0) {
            return i
        }
        i++
    }
}

fun level04b(s : String) : Int {
    val md5 = MessageDigest.getInstance("MD5")
    var i = 0
    while (true) {
        val res = md5.digest((s + i).toByteArray())
        if (res[0] == (0).toByte() && res[1] == (0).toByte() && res[2] == (0).toByte()) {
            return i
        }
        i++
    }
}

fun main() {
    println(level04("yzbqklnj"))
    println(level04b("yzbqklnj"))
}
