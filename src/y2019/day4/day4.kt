package y2019.day4

import resourceText

fun main() {
    val (lowerBound, upperBound) = resourceText(2019, 4).split("-").map {it.toInt()}
    val incrementing = (lowerBound..upperBound).map { it.toString() }
            .filter { (0 until it.lastIndex).all { index -> it[index] <= it[index + 1] } }
    println(incrementing.count {it.toSet().size != it.length})
    println(incrementing.count { pw -> (0 until pw.lastIndex).any { index -> repeatsExactlyTwice(index, pw) } })
}

private fun repeatsExactlyTwice(index: Int, pw: String) =
        notLikePrevious(index, pw) && likeNext(pw, index) && notLikeSecondToNext(index, pw)

private fun notLikeSecondToNext(index: Int, it: String) =
        (index + 1 == it.lastIndex || it[index + 1] != it[index + 2])

private fun likeNext(it: String, index: Int) = (it[index] == it[index + 1])

private fun notLikePrevious(index: Int, it: String) = (index == 0 || it[index - 1] != it[index])
