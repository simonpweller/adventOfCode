package de.sweller.y2018.day2
import de.sweller.resourceLines

fun main() {
    val ids = resourceLines(2018, 2)
    println(part1(ids))
    println(part2(ids))
}

private fun part1(ids: List<String>): Int =
    ids.fold(Pair(0, 0)) { counts, id ->
        Pair(
            counts.first + if (id.groupBy { it }.values.any { it.size == 2 }) 1 else 0,
            counts.second + if (id.groupBy { it }.values.any { it.size == 3 }) 1 else 0
        )
    }.let { it.first * it.second }

private fun part2(ids: List<String>): String =
    ids.flatMap { id1 ->
        ids.mapNotNull { id2 ->
            val sharedChars = id1.indices.mapNotNull { idx -> if (id1[idx] == id2[idx]) id1[idx] else null }
            if (sharedChars.size == id1.length - 1) sharedChars.joinToString("") else null
        }
    }.first()
