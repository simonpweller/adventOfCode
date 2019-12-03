package y2017.day7

import resourceLines

fun main() {
    val lines = resourceLines(2017, 7)
    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): String {
    val programs = lines.map { it.substringBefore(" ") }
    val supportedPrograms = lines.map { it.substringAfter(" -> ") }.filter { it != "" }.flatMap { it.split(", ") }
    return programs.minus(supportedPrograms).first()
}

private fun part2(lines: List<String>): Int {
    val programs = lines.fold(mapOf<String, Program>()) {map, line -> map.plus(
        Pair(
            line.substringBefore(" "),
            Program(
                weight = line.substringAfter("(").substringBefore(")").toInt(),
                supports = if (line.contains(" -> ")) line.substringAfter(" -> ").split(", ") else listOf()
            )
        )
    ) }
    val weights = programs.filterValues { it.supports.isEmpty() }.mapValues { it.value.weight }.toMutableMap()
    val towers = programs.filterValues { it.supports.isNotEmpty() }.toMutableMap()
    while (true) {
        val tower = towers.entries.first { it.value.supports.all { name -> weights.containsKey(name) } }
        val supportedWeights = tower.value.supports.map { weights[it] ?: error("weight not found")}
        if (supportedWeights.toSet().size > 1) {
            val indexOfTowerToAdjust = supportedWeights.indexOfFirst {weight -> supportedWeights.count { it == weight} == 1 }
            val necessaryAdjustment = supportedWeights.first { it != supportedWeights[indexOfTowerToAdjust]} - supportedWeights[indexOfTowerToAdjust]
            return (programs[tower.value.supports[indexOfTowerToAdjust]] ?: error("program not found")).weight + necessaryAdjustment
        }
        val totalWeight = tower.value.weight + supportedWeights.sum()
        weights[tower.key] = totalWeight
        towers.remove(tower.key)
    }
}

data class Program(val weight: Int, val supports: List<String>)
