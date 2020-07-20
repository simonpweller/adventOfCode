package de.sweller.y2015.day13

import permutations
import de.sweller.resourceLines

fun main() {
    val lines = resourceLines(2015, 13)
    val happinessImpact = parseHappinessImpact(lines)
    val setOfPeople = happinessImpact.keys.toSet()
    println(getHappinessBenefit(permutations(setOfPeople), happinessImpact))
    println(getHappinessBenefit(permutations(setOfPeople.plus("Me")), happinessImpact))
}

private fun getHappinessBenefit(arrangements: List<List<String>>, happinessImpact: Map<String, Map<String, Int>>): Int {
    return arrangements.map {
        var totalBenefit = 0
        for (personIndex in 0 until it.lastIndex) {
            val currentPerson = it[personIndex]
            val nextPerson = it[personIndex + 1]
            totalBenefit += happinessImpact[currentPerson]?.get(nextPerson) ?: 0
            totalBenefit += happinessImpact[nextPerson]?.get(currentPerson) ?: 0
        }
        totalBenefit += happinessImpact[it.first()]?.get(it.last()) ?: 0
        totalBenefit += happinessImpact[it.last()]?.get(it.first()) ?: 0
        totalBenefit
    }.max() as Int
}


fun parseHappinessImpact(lines: List<String>): Map<String, Map<String, Int>> {
    val happinessImpactMap = mutableMapOf<String, MutableMap<String, Int>>()
    lines.forEach {
        val words = it.replace(".", "").split(" ")
        val happinessImpact: Int = if (words[2] == "gain") words[3].toInt() else -words[3].toInt()
        val personA = words.first()
        val personB = words.last()
        happinessImpactMap.getOrPut(personA, ::mutableMapOf)[personB] = happinessImpact
    }
    return happinessImpactMap
}

