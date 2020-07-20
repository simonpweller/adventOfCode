package de.sweller.y2016.day20

import de.sweller.resourceLines
import java.lang.Long.max

fun main() {
    val upperLimitExclusive = 4294967296L

    val blockedRanges = resourceLines(2016, 20)
        .map { range -> range.split("-").map { it.toLong() } }
        .map { LongRange(it.first(), it.last()) }
        .sortedBy { it.first }

    val reducedRanges = blockedRanges.plusElement(LongRange(upperLimitExclusive, upperLimitExclusive))
        .fold(listOf()) { acc: List<LongRange>, curr: LongRange ->
            val prev = acc.lastOrNull() ?: LongRange(0, 0)
            if (prev.last >= curr.first) {
                acc.dropLast(1).plusElement(LongRange(prev.first, max(prev.last, curr.last)))
            } else {
                acc.plusElement(curr)
            }
    }

    println(generateSequence(0) { it + 1}.first {reducedRanges.none { range -> range.contains(it) }})
    println(reducedRanges.fold(RunningResult(0L, 0L)) { runningResult, blockedRange ->
        RunningResult(blockedRange.last + 1, runningResult.ipCount + max(0, blockedRange.first - runningResult.next))
    }.ipCount)
}

class RunningResult(val next: Long, val ipCount: Long)