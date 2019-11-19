package y2016.day6

import java.io.File

fun main() {
    val lines = File("src/y2016/day6/day6.txt").readLines()
    println(correctErrors(lines, ::maxCharCount))
    println(correctErrors(lines, ::minCharCount))
}

fun correctErrors(lines: List<String>, heuristic: (List<Pair<Char, Int>>) -> Char?): String {
    val listOfCharCountsAtIndices = lines.fold(List(lines.first().length) { mapOf<Char, Int>() }) { list, line ->
        list.mapIndexed { index, map ->
            map.plus(Pair(line[index], map.getOrDefault(line[index], 0) + 1))
        }
    }
    return listOfCharCountsAtIndices.map { it.toList() }.map { heuristic(it) }.joinToString("")
}

fun minCharCount(list: List<Pair<Char, Int>>): Char? = list.minBy { it.second }?.first
fun maxCharCount(list: List<Pair<Char, Int>>): Char? = list.maxBy { it.second }?.first