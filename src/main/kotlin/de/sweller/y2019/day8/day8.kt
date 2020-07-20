package de.sweller.y2019.day8

import resourceText

fun main() {
    val image = Image(resourceText(2019, 8).map { it.toString().toInt() })
    println(part1(image))
    println(image)
}

private fun part1(image: Image) =
    image.layers
        .minBy { it.count { pixel -> pixel == BLACK } }
        ?.run { this.count { it == WHITE } * this.count { it == TRANSPARENT} }

const val BLACK = 0
const val WHITE = 1
const val TRANSPARENT = 2
const val FULL_BLOCK = "\u2588"

class Image(pixels: List<Int>, private val width: Int = 25, height: Int = 6) {
    val layers = pixels.chunked(width * height)
    private fun getTopLayer(): List<Int> = layers.reduce { prev, curr ->
        prev.zip(curr) { prevPixel, currPixel -> if (prevPixel == TRANSPARENT) currPixel else prevPixel}
    }

    override fun toString(): String =
        getTopLayer().map { if (it == WHITE) FULL_BLOCK else " " }
            .chunked(width).joinToString("\n") { line -> line.joinToString("") }
}