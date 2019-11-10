import java.io.File

fun main() {
    val ips = File("src/day7.txt").readLines().map { IP(it) }
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
        (0 .. sequence.lastIndex - 3).any{ index ->
            sequence[index] == sequence[index + 3] && sequence[index + 1] == sequence[index + 2] && sequence[index] != sequence[index + 1]
        }

    private fun getABAs(sequence: String): List<String> =
        (0 .. sequence.lastIndex - 2).map { index -> sequence.substring(index, index + 3) }.filter {
            it[0] == it[2] && it[0] != it[1]
        }
}

