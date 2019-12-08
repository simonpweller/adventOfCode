package y2019.day8

import intCount
import resourceText

fun main() {
    val image = Image(resourceText(2019, 8).map { it.toString().toInt() })
    println(part1(image))
    println(image)
}

private fun part1(image: Image) =
    image.layers.map { layer -> intCount(layer) }.minBy { it.getOrDefault(BLACK, 0) }!!
        .run { this.getOrDefault(WHITE, 0) * this.getOrDefault(TRANSPARENT, 0) }
