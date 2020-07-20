package de.sweller

import java.io.File

fun resourceLines(year: Int, day: Int) = File("src/main/kotlin/de/sweller/y$year/day$day/input.txt").readLines()
fun resourceText(year: Int, day: Int) = File("src/main/kotlin/de/sweller/y$year/day$day/input.txt").readText().trim()
