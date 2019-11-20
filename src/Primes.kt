import kotlin.math.sqrt

fun main() {
    println(isPrime(93))
}

fun isPrime(num: Int): Boolean = when(num) {
    1 -> false
    2 -> true
    else -> !(num % 2 == 0 || (3 .. sqrt(num.toDouble()).toInt() step 2).any { num % it == 0 })
}