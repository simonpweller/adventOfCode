package y2019.day14

import resourceLines
import kotlin.math.ceil

fun main() {
    val reactions = resourceLines(2019, 14).map(::Reaction)
    val map = reactions.fold(mapOf<String, Pair<Long, List<Resource>>>()) {
        acc, reaction -> acc.plus(reaction.result.type to Pair(reaction.result.amount, reaction.inputs))
    }
    println(part1(map, 1L))
    println(part2(map))
}

private fun part1(map: Map<String, Pair<Long, List<Resource>>>, fuelAmount: Long): Long {
    var simplified = simplifyResources(Resource(fuelAmount, "FUEL"), map)
    while (simplified.size > 1 || simplified.first().type != "ORE") {
        simplified = flattenResources(simplified.flatMap { resource ->
            simplifyResources(
                resource,
                map,
                isIndependent(resource, simplified, map)
            )
        })
    }
    return simplified.first().amount
}

private fun part2(map: Map<String, Pair<Long, List<Resource>>>): Long {
    var amount = 0L
    var oreCost = 0L
    while (oreCost <= 1000000000000) {
        amount += 100000
        oreCost = part1(map, amount)
    }
    while (oreCost >= 1000000000000) {
        amount -= 1000
        oreCost = part1(map, amount)
    }
    while (oreCost <= 1000000000000) {
        amount++
        oreCost = part1(map, amount)
    }
    return amount - 1
}


private fun isIndependent(resource: Resource, resources: List<Resource>, map: Map<String, Pair<Long, List<Resource>>>): Boolean {
    if (resource.type == "ORE") return false
    return resources.filter { it.type != "ORE" }.none { otherResource -> isADependentOnB(otherResource, resource, map) }
}

private fun isADependentOnB(a: Resource, b: Resource, map: Map<String, Pair<Long, List<Resource>>>): Boolean {
    return dependencies(a.type, map).contains(b.type)
}

private fun dependencies(resourceType: String, map: Map<String, Pair<Long, List<Resource>>>): Set<String> {
    val directDependencies = map.getValue(resourceType).second.filter { it.type != "ORE" }.map { it.type }.toSet()
    if (directDependencies.isEmpty()) return emptySet()
    return directDependencies.plus(directDependencies.flatMap { dependencies(it, map) })
}

private fun simplifyResources(
    resource: Resource,
    map: Map<String, Pair<Long, List<Resource>>>,
    roundUp: Boolean = false
): List<Resource> {
    if (resource.type == "ORE") return listOf(resource)
    val (amount, inputs) = map.getValue(resource.type)
    val numberOfRecipes = ceil(resource.amount.toDouble() / amount.toDouble()).toLong()
    if (numberOfRecipes * amount != resource.amount && !roundUp) return listOf(resource)
    val simplifiedResources = inputs
        .map { input -> simplifyResources(input, map) }.flatten()
        .map { it.copy(amount = it.amount * numberOfRecipes) }
    return flattenResources(simplifiedResources)
}

private fun flattenResources(resources: List<Resource>): List<Resource> = resources.fold(listOf()) { acc, nextResource ->
    val existingResource = acc.find { it.type == nextResource.type }
    if (existingResource != null) {
        existingResource.amount += nextResource.amount
        acc
    } else acc.plus(nextResource)
}

private data class Reaction(val result: Resource, val inputs: List<Resource>) {
    constructor(reaction: String): this(
        Resource(reaction.substringAfter(" => ")),
        reaction.substringBefore(" => ").split(", ").map(::Resource)
    )
}

private data class Resource(var amount: Long, val type: String) {
    constructor(resource: String): this(
        resource.split(" ").first().toLong(),
        resource.split(" ").last()
    )
}
