import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val reports = input.map { it.split(" ").filter { it.length > 0 }.map { it.toInt() } }
        var safeReports = 0
        for (report in reports) {
            // if size == 1 then it's safe for sure
            if (report.size < 2) {
                safeReports++
                continue
            }
            val isIncreasing = report[0] <= report[1]
            for (level in 1 until report.size) {
                val distance = abs(report[level - 1] - report[level])
                if (distance > 3 || distance < 1) break
                if (isIncreasing && report[level - 1] > report[level]) {
                    break
                } else if (!isIncreasing && report[level - 1] < report[level]) {
                    break
                }
                if (level == report.size - 1) {
                    safeReports++
                }
            }
        }

        return safeReports
    }

    fun isSafeReport(report: List<Int>, numBadLevels: Int): Boolean {
        // if size == 1 then it's safe for sure
        if (report.size < 2) {
            return true
        }
        if (numBadLevels > 1) return false

        var isIncreasing: Boolean? = null
        for (i in 1 until report.size) {
            if (report[i] == report[0])
                continue
            if (report[i] > report[0]) {
                isIncreasing = true
                break
            } else {
                isIncreasing = false
                break
            }
        }

        if (isIncreasing == null) return true

        for (level in 1 until report.size) {
            val distance = abs(report[level - 1] - report[level])
            if (distance > 3 || distance < 1) {
                if (level >= 2) {
                    val reportMutableFirst = report.toMutableList()
                    reportMutableFirst.removeAt(level - 2)
                    val reportMutableSecond = report.toMutableList()
                    reportMutableSecond.removeAt(level - 1)
                    val reportMutableThird = report.toMutableList()
                    reportMutableThird.removeAt(level)
                    return isSafeReport(reportMutableFirst, numBadLevels + 1) || isSafeReport(
                        reportMutableSecond,
                        numBadLevels + 1
                    ) || isSafeReport(reportMutableThird, numBadLevels + 1)
                } else {
                    val reportMutableSecond = report.toMutableList()
                    reportMutableSecond.removeAt(0)
                    val reportMutableThird = report.toMutableList()
                    reportMutableThird.removeAt(level)
                    return isSafeReport(
                        reportMutableSecond,
                        numBadLevels + 1
                    ) || isSafeReport(reportMutableThird, numBadLevels + 1)
                }
            }
            if (isIncreasing && report[level - 1] > report[level]) {
                if (level >= 2) {
                    val reportMutableFirst = report.toMutableList()
                    reportMutableFirst.removeAt(level - 2)
                    val reportMutableSecond = report.toMutableList()
                    reportMutableSecond.removeAt(level - 1)
                    val reportMutableThird = report.toMutableList()
                    reportMutableThird.removeAt(level)
                    return isSafeReport(reportMutableFirst, numBadLevels + 1) || isSafeReport(
                        reportMutableSecond,
                        numBadLevels + 1
                    ) || isSafeReport(reportMutableThird, numBadLevels + 1)
                } else {
                    val reportMutableSecond = report.toMutableList()
                    reportMutableSecond.removeAt(0)
                    val reportMutableThird = report.toMutableList()
                    reportMutableThird.removeAt(level)
                    return isSafeReport(
                        reportMutableSecond,
                        numBadLevels + 1
                    ) || isSafeReport(reportMutableThird, numBadLevels + 1)
                }

            }
            if (!isIncreasing && report[level - 1] < report[level]) {

                if (level >= 2) {
                    val reportMutableFirst = report.toMutableList()
                    reportMutableFirst.removeAt(level - 2)
                    val reportMutableSecond = report.toMutableList()
                    reportMutableSecond.removeAt(level - 1)
                    val reportMutableThird = report.toMutableList()
                    reportMutableThird.removeAt(level)
                    return isSafeReport(reportMutableFirst, numBadLevels + 1) || isSafeReport(
                        reportMutableSecond,
                        numBadLevels + 1
                    ) || isSafeReport(reportMutableThird, numBadLevels + 1)
                } else {
                    val reportMutableSecond = report.toMutableList()
                    reportMutableSecond.removeAt(0)
                    val reportMutableThird = report.toMutableList()
                    reportMutableThird.removeAt(level)
                    return isSafeReport(
                        reportMutableSecond,
                        numBadLevels + 1
                    ) || isSafeReport(reportMutableThird, numBadLevels + 1)
                }

            }
            if (level == report.size - 1) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { it.split(" ").filter { it.length > 0 }.map { it.toInt() } }
        var safeReports = 0
        for (report in reports) {
            if (isSafeReport(report, 0))
                safeReports++
        }

        return safeReports
    }

    check(part2(listOf("7 6 4 2 1")) == 1)
    check(part2(listOf("1 2 7 8 9")) == 0)
    check(part2(listOf("9 7 6 2 1")) == 0)
    check(part2(listOf("1 3 2 4 5")) == 1)
    check(part2(listOf("8 6 4 4 1")) == 1)
    check(part2(listOf("1 3 6 7 9")) == 1)
    check(part2(listOf("9 4 3 2 1")) == 1)
    check(part2(listOf("9 7 3 2 1")) == 0)
    check(part2(listOf("6 5 3 2 5")) == 1)
    check(part2(listOf("6 5 3 5 2")) == 1)
    check(part2(listOf("5 9 3 5 2")) == 0)
    check(part2(listOf("4 4 3 2 1")) == 1)
    check(part2(listOf("4 5 4 2 1")) == 1)
    check(part2(listOf("4 4 4 5 2 1")) == 0)
    check(part2(listOf("4 3 4 2 1")) == 1)
    check(part2(listOf("4 3 2 1 5")) == 1)
    check(part2(listOf("4 3 2 1 5 5")) == 0)
    check(part2(listOf("4 3 2 1 2")) == 1)
    check(part2(listOf("2 3 2 1")) == 1)


    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
