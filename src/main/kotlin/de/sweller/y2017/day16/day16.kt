package de.sweller.y2017.day16

import de.sweller.resourceText
import java.lang.IllegalArgumentException

fun main() {
    val moves = resourceText(2017, 16).toMoves()
    val programs = ('a'..'p').toList()
    println(part1(programs, moves).joinToString(""))
    println(part2(programs, moves).joinToString(""))
}

fun part2(programs: List<Char>, moves: List<Move>): List<Char> =
        generateSequence(programs) { part1(it, moves) }.drop(1)
                .take(1_000_000_000 % getCycleLength(programs, moves)).last()

fun getCycleLength(programs: List<Char>, moves: List<Move>): Int =
        generateSequence(programs) { part1(it, moves) }.drop(1)
                .takeWhile { it != programs }.toList().size + 1

fun part1(programs: List<Char>, moves: List<Move>): List<Char> =
        moves.fold(Dance(programs)) { dance, program -> dance.makeMove(program)}.programs

fun String.toMoves(): List<Move> = this.split(",").map {
    when {
        it.startsWith("s") -> Spin(it.substringAfter("s").toInt())
        it.startsWith("x") -> Exchange(
                it.substringAfter("x").substringBefore("/").toInt(),
                it.substringAfter("/").toInt())
        it.startsWith("p") -> Partner(
                it.substringAfter("p").first(),
                it.substringAfter("/").first()
        )
        else -> throw IllegalArgumentException("Unknown move")
    }
}

sealed class Move
class Spin(val size: Int) : Move()
class Exchange(val a: Int, val b: Int) : Move()
class Partner(val a: Char, val b: Char) : Move()

class Dance(val programs: List<Char>) {
    fun makeMove(move: Move): Dance = when (move) {
        is Spin -> spin(move.size)
        is Exchange -> exchange(move.a, move.b)
        is Partner -> partner(move.a, move.b)
    }

    private fun spin(size: Int): Dance = Dance(programs.takeLast(size) + programs.dropLast(size))

    private fun exchange(a: Int, b: Int): Dance = Dance(programs.toMutableList().apply {
        this[a] = programs[b]
        this[b] = programs[a]
    })

    private fun partner(a: Char, b: Char): Dance = Dance(programs.toMutableList().apply {
        this[programs.indexOf(a)] = b
        this[programs.indexOf(b)] = a
    })
}