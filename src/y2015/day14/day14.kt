package y2015.day14

import resourceLines

fun main() {
    val lines = resourceLines(2015, 14)
    val reindeer = parseReindeer(lines)
    repeat(2503) {
        reindeer.forEach{it.act()}
        val leadingDistance = reindeer.map { it.distanceTravelled }.max()
        reindeer.filter{ it.distanceTravelled == leadingDistance}.forEach { it.pointsScored++}
    }
    println(reindeer.map { it.distanceTravelled }.max())
    println(reindeer.map { it.pointsScored }.max())
}

fun parseReindeer(lines: List<String>): List<Reindeer> {
    return lines.map {
        val words = it.split(" ")
        val kms = words[3].toInt()
        val flyingDuration = words[6].toInt()
        val restingDuration = words[13].toInt()
        return@map Reindeer(kms, flyingDuration, restingDuration)
    }
}

class Reindeer(private val kms: Int, private val flyingDuration: Int, private val restingDuration: Int) {
    private var currentActivity: Activity = Activity.FLYING
    private var remainingDuration: Int = flyingDuration
    var distanceTravelled: Int = 0
    var pointsScored: Int = 0

    fun act() {
        if (remainingDuration == 0) {
            switchActivity()
        }
        if (currentActivity == Activity.FLYING) {
            distanceTravelled += kms
        }
        remainingDuration--
    }

    private fun switchActivity() {
        when (currentActivity) {
            Activity.FLYING -> {
                currentActivity = Activity.RESTING
                remainingDuration = restingDuration
            }
            Activity.RESTING -> {
                currentActivity = Activity.FLYING
                remainingDuration = flyingDuration
            }
        }
    }

    enum class Activity {
        FLYING,
        RESTING
    }
}