package y2019.day14

import resourceLines

fun main() {
    val reactions = resourceLines(2019, 14).map(::Reaction)
    println(reactions)
}

data class Reaction(val result: Resource, val inputs: ResourceList) {
    constructor(reaction: String): this(
        Resource(reaction.substringAfter(" => ")),
        ResourceList(reaction.substringBefore(" => "))
    )
}

data class ResourceList(val resources: MutableList<Resource>) {
    constructor(resourceList: String): this(
        resourceList.split(", ").map(::Resource).toMutableList()
    )

    operator fun plus(additionalResources: ResourceList) {
        additionalResources.resources.forEach { additionalResource ->
            val existingResource = resources.find { it.type == additionalResource.type }
            if (existingResource == null) {
                resources.add(additionalResource)
            } else {
                existingResource.amount += additionalResource.amount
            }
        }
    }

    operator fun minus(resource: Resource) {
        val existingResource = resources.find { it.type == resource.type}
        if (existingResource == null || existingResource.amount < resource.amount) error("insufficient resources")
        existingResource.amount -= resource.amount
    }
}

data class Resource(var amount: Int, val type: String) {
    constructor(resource: String): this(
        resource.split(" ").first().toInt(),
        resource.split(" ").last()
    )
}
