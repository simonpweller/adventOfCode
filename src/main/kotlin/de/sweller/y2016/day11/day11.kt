package de.sweller.y2016.day11

fun main() {
    val firstFloor = Floor(
        setOf(
            Item("polonium", ItemType.GENERATOR),
            Item("thulium", ItemType.GENERATOR),
            Item("thulium", ItemType.MICROCHIP),
            Item("promethium", ItemType.GENERATOR),
            Item("ruthenium", ItemType.GENERATOR),
            Item("ruthenium", ItemType.MICROCHIP),
            Item("cobalt", ItemType.GENERATOR),
            Item("cobalt", ItemType.MICROCHIP)
        )
    )
    val secondFloor = Floor(
        setOf(
            Item("polonium", ItemType.MICROCHIP),
            Item("promethium", ItemType.MICROCHIP)
        )
    )
    val floors = listOf(firstFloor, secondFloor, Floor(), Floor())
    println(getNeededSteps(FloorPlan(floors)))
}

fun getNeededSteps(floorPlan: FloorPlan): Int? {
    var steps = 0
    var currentStates = listOf(floorPlan)
    var eliminatedStates = hashSetOf<FloorPlan>()

    while (currentStates.isNotEmpty()) {
        if (currentStates.any { it.isWinning}) return steps
        eliminatedStates = eliminatedStates.plus(currentStates).toHashSet()
        steps++
        currentStates = currentStates.map { it.adjacentFloorPlans }.flatten().toSet().toList()
        currentStates = currentStates.filter { !eliminatedStates.contains(it) }
        eliminatedStates.addAll(currentStates.filter { it.isIllegal })
        currentStates = currentStates.filter { !it.isIllegal }
    }
    return null
}

data class FloorPlan(val floors: List<Floor> = listOf(), val elevatorFloor: Int = 0) {
    val isWinning: Boolean
        get() = floors.last().itemCount == floors.sumBy(Floor::itemCount)

    val isIllegal: Boolean
        get() = floors.any(Floor::isIllegal)

    val adjacentFloorPlans: Set<FloorPlan>
        get() {
            val currentFloor = floors[elevatorFloor]
            return currentFloor.movableItemSets.fold(setOf()) { floorPlans: Set<FloorPlan>, movableItems: Set<Item> ->
                val hasFloorAbove = elevatorFloor != floors.lastIndex
                val hasFloorBelow = elevatorFloor != 0
                val newFloorPlans = mutableListOf<FloorPlan>()
                if (hasFloorAbove) {
                    val newFloorAbove = floors[elevatorFloor + 1].plus(movableItems)
                    val newCurrentFloor = currentFloor.minus(movableItems)
                    val newFloors = floors.subList(0, elevatorFloor) + newCurrentFloor + newFloorAbove + floors.subList(elevatorFloor + 2, floors.size)
                    newFloorPlans.add(FloorPlan(newFloors, elevatorFloor + 1))
                }
                if (hasFloorBelow) {
                    val newFloorBelow = floors[elevatorFloor - 1].plus(movableItems)
                    val newCurrentFloor = currentFloor.minus(movableItems)
                    val newFloors = floors.subList(0, elevatorFloor - 1) + newFloorBelow + newCurrentFloor + floors.subList(elevatorFloor + 1, floors.size)
                    newFloorPlans.add(FloorPlan(newFloors, elevatorFloor - 1))
                }
                floorPlans.plus(newFloorPlans)
            }
        }
}

data class Floor(val items: Set<Item> = setOf()) {
    private val generatorElements = items.filter { it.type == ItemType.GENERATOR }.map { it.element }.toSet()
    private val microChipElements = items.filter { it.type == ItemType.MICROCHIP }.map { it.element }.toSet()
    val itemCount = items.size
    val isIllegal = generatorElements.isNotEmpty() && microChipElements.any { !generatorElements.contains(it) }
    val movableItemSets: Set<Set<Item>>
        get() = subSetsOfSize(items, 1) + subSetsOfSize(items, 2)
    fun minus(itemsToRemove: Set<Item>) = this.copy(items = items.minus(itemsToRemove))
    fun plus(itemsToAdd: Set<Item>) = this.copy(items = items.plus(itemsToAdd))

    private fun <T>subSetsOfSize(set: Set<T>, size: Int): Set<Set<T>> {
        if (size == 1) return set.map { setOf(it) }.toSet()
        return set.map { subSetsOfSize(set.minus(it), size - 1).map { subSet -> subSet.plus(it)} }.flatten().toSet()
    }
}

data class Item(val element: String, val type: ItemType)
enum class ItemType {GENERATOR, MICROCHIP}
