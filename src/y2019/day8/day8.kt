package y2019.day8

import resourceText

fun main() {
    val image = Image(resourceText(2019, 8).map { it.toString().toInt() })
    println(part1(image))
}

private fun part1(image: Image) =
    image.layers.map { layer -> intCount(layer) }.minBy { it.getOrDefault(0, 0) }!!
        .run { this.getOrDefault(1, 0) * this.getOrDefault(2, 0) }

class Image(pixels: List<Int>, width: Int = 25, height: Int = 6) {
    val layers = pixels.chunked(width * height)
}

fun intCount(input: List<Int>): Map<Int, Int> =
    input.fold(mapOf()) { acc, int -> acc.plus(Pair(int, acc.getOrDefault(int, 0 ) + 1))}