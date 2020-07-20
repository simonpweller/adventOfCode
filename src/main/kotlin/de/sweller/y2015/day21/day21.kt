package de.sweller.y2015.day21

import combinations
import resourceLines
import java.lang.Integer.max

fun main() {
    val lines = resourceLines(2015, 21)
    val (bossHitPoints, bossDamage, bossArmor) = lines.map { it.substringAfter(": ").toInt() }

    val equipmentCombos = getEquipmentCombos()
    val cheapestWinningEquipment = equipmentCombos.map { Equipment(it) }.filter {
        val boss = Fighter(bossHitPoints, bossDamage, bossArmor)
        val player = Fighter(100, it.damage, it.armor)
        player.fight(boss)
    }.minBy { it.cost }
    println(cheapestWinningEquipment?.cost)

    val mostExpensiveLosingEquipment = equipmentCombos.map { Equipment(it) }.filter {
        val boss = Fighter(bossHitPoints, bossDamage, bossArmor)
        val player = Fighter(100, it.damage, it.armor)
        !player.fight(boss)
    }.maxBy { it.cost }
    println(mostExpensiveLosingEquipment?.cost)
}

private fun getEquipmentCombos(): List<List<EquipmentPiece>> {
    val weapons = listOf(
        EquipmentPiece("Dagger", 8, damage = 4),
        EquipmentPiece("Shortsword", 10, damage = 5),
        EquipmentPiece("Warhammer", 25, damage = 6),
        EquipmentPiece("Longsword", 40, damage = 7),
        EquipmentPiece("Greataxe", 74, damage = 8)
    )

    val armorPieces = listOf(
        EquipmentPiece("Leather", 13, armor = 1),
        EquipmentPiece("Chainmail", 31, armor = 2),
        EquipmentPiece("Splintmail", 53, armor = 3),
        EquipmentPiece("Bandedmail", 75, armor = 4),
        EquipmentPiece("Platemail", 102, armor = 5),
        null
    )

    val rings = listOf(
        EquipmentPiece("Damage +1", 25, damage = 1),
        EquipmentPiece("Damage +2", 50, damage = 2),
        EquipmentPiece("Damage +3", 100, damage = 3),
        EquipmentPiece("Defense +1", 20, armor = 1),
        EquipmentPiece("Defense +2", 40, armor = 2),
        EquipmentPiece("Defense +3", 80, armor = 3),
        null
    )

    val weaponArmorCombos = combinations(weapons, armorPieces)
        .map{ it.filterNotNull()}
    val ringCombos = combinations(rings, rings)
        .map{ it.filterNotNull()}
        .filter { it.size < 2 || it[0] != it[1] }

    return weaponArmorCombos.map { weaponArmorCombo -> ringCombos.map { ringCombo -> weaponArmorCombo + ringCombo } }
        .flatten()
}

class Fighter(private var hitPoints: Int, private val damage: Int, private val armor: Int) {
    fun fight(enemy: Fighter): Boolean {
        var attackingFighter = this
        var defendingFighter = enemy
        while (hitPoints > 0 && enemy.hitPoints > 0) {
            attackingFighter.attack(defendingFighter)
            attackingFighter = defendingFighter.also { defendingFighter = attackingFighter }
        }
        return hitPoints > 0
    }

    private fun attack(enemy: Fighter) {
        val damage = max(damage - enemy.armor, 1)
        enemy.hitPoints -= damage
    }
}

class Equipment(equipmentPieces: List<EquipmentPiece>) {
    val cost = equipmentPieces.sumBy { it.cost }
    val damage = equipmentPieces.sumBy { it.damage }
    val armor = equipmentPieces.sumBy { it.armor }
}

class EquipmentPiece(private val name: String, val cost: Int, val damage: Int = 0, val armor: Int = 0) {
    override fun toString(): String = name
}

