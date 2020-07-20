package de.sweller.y2016.day10

import resourceLines

fun main() {
    val instructions = resourceLines(2016, 10)
    val bots = mutableMapOf<Int, Bot>()
    val outputs = mutableMapOf<Int, Int>()
    instructions.filter { it.startsWith("value") }.forEach {
        val value = it.substringAfter("value ").substringBefore(" ").toInt()
        val bot = it.substringAfter("bot ").substringBefore(" ").toInt()
        bots[bot] = bots.getOrDefault(bot, Bot()).apply { chips.add(value) }
    }
    while (bots.any { it.value.chips.size == 2 && !it.value.isDone }) {
        bots.filterValues { it.chips.size == 2 && !it.isDone }.forEach { (number, bot) ->
            val instruction = instructions.find { it.startsWith("bot $number ") }
            if (instruction != null) {
                if (instruction.contains("low to bot")) {
                    val botReceivingLow = instruction.substringAfter("low to bot ").substringBefore(" ").toInt()
                    bots[botReceivingLow] = bots.getOrDefault(botReceivingLow, Bot()).apply { chips.add(bot.chips.min()!!) }
                } else {
                    val outputReceivingLow = instruction.substringAfter("low to output ").substringBefore(" ").toInt()
                    outputs[outputReceivingLow] = bot.chips.min()!!
                }
                if (instruction.contains("high to bot")) {
                    val botReceivingHigh = instruction.substringAfter("high to bot ").substringBefore(" ").toInt()
                    bots[botReceivingHigh] = bots.getOrDefault(botReceivingHigh, Bot()).apply { chips.add(bot.chips.max()!!) }
                } else {
                    val outputReceivingHigh = instruction.substringAfter("high to output ").substringBefore(" ").toInt()
                    outputs[outputReceivingHigh] = bot.chips.max()!!
                }
            }
            bot.isDone = true
        }
    }
    println(bots.filterValues { it.chips.containsAll(listOf(61, 17)) }.keys.first())
    println(outputs[0]!! * outputs[1]!! * outputs[2]!!)
}

data class Bot(var chips: MutableList<Int> = mutableListOf(), var isDone: Boolean = false)