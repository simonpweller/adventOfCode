package y2016.day18

import java.io.File


fun main() {
    val firstRow = Floor(File("src/y2016/day18/input.txt").readText().map { Tile(it == '^') })
    println(Dungeon(firstRow, 40).safeTiles)
    println(Dungeon(firstRow, 400000).safeTiles)
}

private class Dungeon(firstRow: Floor, floors: Int) {
    val safeTiles: Int
        get() = floors.sumBy { it.safeTiles }
    override fun toString(): String = floors.joinToString("\n")

    private val floors = generateSequence(firstRow) { previousFloor -> previousFloor.getNextFloor()}.take(floors).toList()
}

private class Floor(val tiles: List<Tile>) {
    fun getNextFloor(): Floor = Floor((0..tiles.lastIndex).map { index -> Tile(getTileAt(index - 1).isTrapped.xor(getTileAt(index + 1).isTrapped))})
    val safeTiles: Int
        get() = tiles.count { !it.isTrapped }
    override fun toString(): String = tiles.joinToString("")

    private fun getTileAt(index: Int): Tile = if (index == - 1 || index == tiles.size) Tile(false) else tiles[index]
}

private class Tile(val isTrapped: Boolean) {
    override fun toString(): String = if(isTrapped) "^" else "."
}