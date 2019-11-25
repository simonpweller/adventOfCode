package day19

import java.io.File

fun main() {
    val lines = File("src/y2015/day19/input.txt").readLines()
    val replacements = mutableMapOf<String, List<String>>()
    val reverseReplacements = mutableMapOf<String, String>()
    lines.filter { it.contains("=>") }.forEach {
        val (atom, replacement) = it.split(" => ")
        replacements[atom] = replacements.getOrDefault(atom, listOf()).plus(replacement)
        reverseReplacements[replacement] = atom
    }

    val atoms = atomsOfMolecule(lines.last())
    val possibleMolecules = possibleMolecules(atoms, replacements)

    println(possibleMolecules.map { it.joinToString("") }.toSet().size)
    var reducedLine = lines.last()
    var swaps = 0
    while (reducedLine != "e") {
        reverseReplacements.keys.forEach{
            if (reducedLine.contains(it)) {
                reducedLine = reducedLine.replaceFirst(it, reverseReplacements[it]!!)
                swaps++
            }
        }
    }
    println(swaps)
}

private fun possibleMolecules(atoms: List<String>, replacements: Map<String, List<String>>): List<List<String>> =
    atoms.mapIndexed { index, atom ->
        replacements.getOrDefault(atom, listOf()).map {
            atoms.subList(0, index).plus(it).plus(atoms.subList(index + 1, atoms.size))
        }
    }.flatten()

private fun atomsOfMolecule(molecule: String): List<String> {
    var currentPieces = ""
    val atoms = mutableListOf<String>()
    molecule.forEach {
        if (it.isUpperCase() && currentPieces.isNotEmpty()) {
            atoms.add(currentPieces)
            currentPieces = it.toString()
        } else {
            currentPieces += it.toString()
        }
    }
    if (currentPieces.isNotEmpty()) atoms.add(currentPieces)
    return atoms
}

