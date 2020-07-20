package de.sweller.y2015.day16

import de.sweller.resourceLines

fun main() {
    val lines = resourceLines(2015, 16)
    val sues = lines.map { parseSue(it) }
    val matchingSuePart1 = sues
        .filter { it.traitEquals("children", 3) }
        .filter { it.traitEquals("cats", 7) }
        .filter { it.traitEquals("samoyeds", 2) }
        .filter { it.traitEquals("pomeranians", 3) }
        .filter { it.traitEquals("akitas", 0) }
        .filter { it.traitEquals("vizslas", 0) }
        .filter { it.traitEquals("goldfish", 5) }
        .filter { it.traitEquals("trees", 3) }
        .filter { it.traitEquals("cars", 2) }
        .filter { it.traitEquals("perfumes", 1) }
        .first()
    println(matchingSuePart1.number)

    val matchingSuePart2 = sues
        .filter { it.traitEquals("children", 3) }
        .filter { it.traitGreaterThan("cats", 7) }
        .filter { it.traitEquals("samoyeds", 2) }
        .filter { it.traitFewerThan("pomeranians", 3) }
        .filter { it.traitEquals("akitas", 0) }
        .filter { it.traitEquals("vizslas", 0) }
        .filter { it.traitFewerThan("goldfish", 5) }
        .filter { it.traitGreaterThan("trees", 3) }
        .filter { it.traitEquals("cars", 2) }
        .filter { it.traitEquals("perfumes", 1) }
        .first()
    println(matchingSuePart2.number)
}

fun parseSue(line: String): Sue {
    val number = line.substringAfter(" ").substringBefore(":")
    val traits = line.substringAfter(": ").split(", ").map{ it.split(": ") }
    val sue = Sue(number.toInt())
    traits.forEach {
        val (traitName, traitValue) = it
        sue.traits[traitName] = traitValue.toInt()
    }
    return sue
}

class Sue(val number: Int) {
    val traits = mutableMapOf<String, Int>()

    fun traitEquals(trait: String, value: Int): Boolean {
        return traits[trait] == null || traits[trait] == value
    }

    fun traitGreaterThan(trait: String, value: Int): Boolean {
        return traits[trait] == null || traits[trait]!! > value
    }

    fun traitFewerThan(trait: String, value: Int): Boolean {
        return traits[trait] == null || traits[trait]!! < value
    }
}