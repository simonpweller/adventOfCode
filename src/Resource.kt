import java.io.File

fun resourceLines(year: Int, day: Int) = File("src/y$year/day$day/input.txt").readLines()