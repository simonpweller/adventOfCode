package utils

/**
 * Returns a list of all ways to place m balls in n buckets (of unlimited size)
 * weakCompositions(5, 2) -> [[0, 5], [1, 4], [2, 3], [3, 2], [4, 1], [5, 0]]
 */
fun weakCompositions(balls: Int, buckets: Int, parent: List<Int> = listOf()): List<List<Int>> {
    if (buckets == 1) {
        return listOf(parent.plus(balls))
    }
    if (balls == 0) {
        return listOf(parent.plus(intArrayOf(buckets, 0).toList()))
    }
    return (0..balls)
        .map { weakCompositions(balls - it, buckets - 1, parent.plus(it))}
        .flatten()
}