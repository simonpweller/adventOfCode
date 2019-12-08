package y2019.day8

const val BLACK = 0
const val WHITE = 1
const val TRANSPARENT = 2

class Image(pixels: List<Int>, private val width: Int = 25, height: Int = 6) {
    val layers = pixels.chunked(width * height)
    private fun getTopLayer(): List<Int> = layers.reduce { acc, curr ->
        acc.zip(curr) { accPixel, currPixel -> if (accPixel == TRANSPARENT) currPixel else accPixel}
    }

    override fun toString(): String =
        getTopLayer().map { if (it == 1) '*' else ' ' }
            .chunked(width).joinToString("\n") { line -> line.joinToString("") }
}