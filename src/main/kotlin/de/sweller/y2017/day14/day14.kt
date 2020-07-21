package de.sweller.y2017.day14

import de.sweller.resourceText
import de.sweller.y2017.day10.knotHash
import kotlin.math.abs

fun main() {
    val disk = Disk(resourceText(2017, 14))
    println(disk.occupiedBytesCount)
    println(disk.regionCount)
}

class Disk(key: String) {
    private val byteStrings: List<String> = (0 until 128)
            .map { row ->
                knotHash("$key-$row")
                        .map { it.toString().toInt(16).toString(2).padStart(4, '0') }
                        .joinToString("")
            }

    val occupiedBytesCount: Int
        get() = occupiedBytes.size

    val regionCount: Int
        get() {
            var regions = occupiedBytes.map { Region(mutableListOf(it)) }
            while (true) {
                val nextRegions = defragment(regions)
                if (regions.size == nextRegions.size) return regions.size
                regions = nextRegions
            }
        }

    private fun defragment(regions: List<Region>): List<Region> {
        regions.forEach { region1 ->
            regions.minus(region1).forEach { region2 ->
                if (region1.isAdjacentTo(region2)) {
                    region1 += region2
                    return regions.minus(region2)
                }
            }
        }
        return regions
    }

    private val occupiedBytes: List<DiskLocation>
        get() = byteStrings.foldIndexed(listOf()) { rowIndex: Int, diskLocations: List<DiskLocation>, row: String ->
            diskLocations + row.mapIndexedNotNull { colIndex, c -> if (c == '1') DiskLocation(rowIndex, colIndex) else null }
        }

    class Region(private val diskLocations: MutableList<DiskLocation>) {
        fun isAdjacentTo(other: Region): Boolean {
            return diskLocations.any { diskLocation -> other.diskLocations.any { diskLocation.isAdjacentTo(it) } }
        }

        operator fun plusAssign(other: Region) {
            diskLocations.addAll(other.diskLocations)
        }
    }

    class DiskLocation(private val row: Int, private val col: Int) {
        fun isAdjacentTo(other: DiskLocation): Boolean {
            return abs(this.row - other.row) + abs(this.col - other.col) == 1
        }
    }
}