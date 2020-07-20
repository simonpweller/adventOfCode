package de.sweller.y2015.day22

import resourceLines
import java.lang.Integer.MAX_VALUE
import java.lang.Integer.max

var currentMin = MAX_VALUE

fun main() {
    val lines = resourceLines(2015, 22)
    val (hitPoints, damage) = lines.map{it.substringAfter(": ").toInt()}
    println(BattleState(bossDamage = damage, bossHitPoints = hitPoints).getMinManaSpentToWin())
    currentMin = MAX_VALUE
    println(BattleState(bossHitPoints = hitPoints, bossDamage = damage, hardMode = true).getMinManaSpentToWin())
}

data class BattleState(
    val totalManaSpent: Int = 0,
    val bossDamage: Int,
    var currentMana: Int = 500,
    var wizardHitPoints: Int = 50,
    var bossHitPoints: Int,
    var shieldEffect: Int = 0,
    var poisonEffect: Int = 0,
    var rechargeEffect: Int = 0,
    val hardMode: Boolean = false
) {
    fun getMinManaSpentToWin(): Int? {
        if (totalManaSpent > currentMin) return null // already have a better solution, bail early
        val minManaSpent = calculateMinManaSpentToWin()
        if (minManaSpent != null && minManaSpent < currentMin) {
            currentMin = minManaSpent
        }
        return minManaSpent
    }

    private fun calculateMinManaSpentToWin(): Int? {
        if (hardMode) wizardHitPoints--
        if (wizardHitPoints <= 0) return null
        applyEffects()
        if (bossHitPoints <= 0) return totalManaSpent

        return getValidSpells().map { castSpell(it) }.mapNotNull {
            if (it.bossHitPoints > 0) it.applyEffects()
            if (it.bossHitPoints > 0) it.applyBossAttack()
            it.getMinManaSpentToWin()
        }.min()
    }


    private fun getValidSpells(): List<Spell> = Spell.values().filter { it.isValid(this) }
    private fun castSpell(spell: Spell): BattleState {
        val nextState = this.copy(
            currentMana = currentMana - spell.cost,
            totalManaSpent = totalManaSpent + spell.cost
        )
        return spell.applyEffects(nextState)
    }
    private fun applyEffects() {
        if (shieldEffect > 0) shieldEffect--
        if (poisonEffect > 0) {
            bossHitPoints -= 3
            poisonEffect--
        }
        if (rechargeEffect > 0) {
            currentMana += 101
            rechargeEffect--
        }
    }
    private fun applyBossAttack() {
        wizardHitPoints -= max(if (shieldEffect > 0) bossDamage - 7 else bossDamage, 1)
    }
}

enum class Spell {
    MAGIC_MISSILE,
    DRAIN,
    SHIELD,
    POISON,
    RECHARGE;

    val cost: Int
        get() = when (this) {
            MAGIC_MISSILE -> 53
            DRAIN -> 73
            SHIELD -> 113
            POISON -> 173
            RECHARGE -> 229
        }

    fun isValid(battleState: BattleState): Boolean =
        when (this) {
            MAGIC_MISSILE -> battleState.currentMana >= cost
            DRAIN -> battleState.currentMana >= cost
            SHIELD -> battleState.currentMana >= cost && battleState.shieldEffect == 0
            POISON -> battleState.currentMana >= cost && battleState.poisonEffect == 0
            RECHARGE -> battleState.currentMana >= cost && battleState.rechargeEffect == 0
        }

    fun applyEffects(battleState: BattleState): BattleState =
        when (this) {
            MAGIC_MISSILE -> battleState.copy(bossHitPoints = battleState.bossHitPoints - 4)
            DRAIN -> battleState.copy(bossHitPoints = battleState.bossHitPoints - 2, wizardHitPoints = battleState.wizardHitPoints + 2)
            SHIELD -> battleState.copy(shieldEffect = 6)
            POISON -> battleState.copy(poisonEffect = 6)
            RECHARGE -> battleState.copy(rechargeEffect = 5)
        }
}

