package de.sweller.y2016.day6

import resourceLines

fun main() {
    val lines = resourceLines(2016, 6)
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