package y2016.day7

import java.io.File

fun main() {
    val ips = File("src/y2016/day7/day7.txt").readLines().map { IP(it) }
    println(ips.filter{it.supportsTSL}.size)
    println(ips.filter{it.supportsSSL}.size)
}

class IP(ip: String) {
    private val supernetSequences = ip.replace("\\[\\w*]".toRegex(), "|").split("|")
    private val hypernetSequences = "\\[(?<hypernetSequence>\\w*)]".toRegex().findAll(ip).toList().map { it.groups["hypernetSequence"]!!.value }

    val supportsTSL: Boolean
        get() = supernetSequences.any(::hasABBA) && hypernetSequences.none(::hasABBA)

    val supportsSSL: Boolean
        get() = getABAs().any { aba -> hasCorrespondingBAB(aba) }

    private fun getABAs(): List<String> = supernetSequences.map { getABAs(it) }.flatten()

    private fun hasCorrespondingBAB(aba: String) = hypernetSequences.any { it.contains(getCorrespondingBAB(aba)) }

    private fun getCorrespondingBAB(aba: String) = aba.substring(1, 2) + aba.substring(0, 1) + aba.substring(1, 2)

    private fun hasABBA(sequence: String): Boolean =
        (0 .. sequence.lastIndex - 3).map { index -> sequence.substring(index, index + 4) }.any{ substring ->
            substring[0] == substring[3] && substring[1] == substring[2] && substring[0] != substring[1]
        }

    private fun getABAs(sequence: String): List<String> =
        (0 .. sequence.lastIndex - 2).map { index -> sequence.substring(index, index + 3) }.filter { substring ->
            substring[0] == substring[2] && substring[0] != substring[1]
        }
}

