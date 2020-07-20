package de.sweller.y2015.day15

import de.sweller.resourceLines
import weakCompositions


fun main() {
    val lines = resourceLines(2015, 15)
    val ingredients = parseIngredients(lines)

    val recipes = weakCompositions(100, ingredients.size)
    println(recipes.map { scoreRecipe(it, ingredients) }.max())
    println(recipes.filter { countCalories(it, ingredients) == 500}
        .map { scoreRecipe(it, ingredients) }.max()
    )
}

fun scoreRecipe(recipe: List<Int>, ingredients: List<Ingredient>): Int {
    val capacity = recipe.mapIndexed { index, amount -> ingredients[index].capacity * amount }.sum()
    val durability = recipe.mapIndexed { index, amount -> ingredients[index].durability * amount }.sum()
    val flavor = recipe.mapIndexed { index, amount -> ingredients[index].flavor * amount }.sum()
    val texture = recipe.mapIndexed { index, amount -> ingredients[index].texture * amount }.sum()
    return maxOf(0, capacity) * maxOf(0, durability) * maxOf(0, flavor) * maxOf(0, texture)
}

fun countCalories(recipe: List<Int>, ingredients: List<Ingredient>): Int {
    return recipe.mapIndexed { index, amount -> ingredients[index].calories * amount }.sum()
}

fun parseIngredients(lines: List<String>): List<Ingredient> {
    return lines.map {
        val words = it.replace(",", "").split(" ")
        return@map Ingredient(words[2].toInt(), words[4].toInt(), words[6].toInt(), words[8].toInt(), words[10].toInt())
    }
}

data class Ingredient(val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)