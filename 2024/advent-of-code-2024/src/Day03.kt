import kotlin.math.abs

fun main() {
    fun readNumber(fromString: String): Pair<Int, Int> {
        val numberString = StringBuilder()
        var lastIndex = 0
        for (i in 0 until fromString.length) {
            if (fromString[i].isDigit()) {
                numberString.append(fromString[i])
                lastIndex++
            } else break
        }
        return numberString.toString().toInt() to lastIndex
    }

    fun part1(input: List<String>): Int {
        var mulValue = 0
        for (line in input) {
            var currentIndex = 0
            while (currentIndex < line.length) {
                if (currentIndex + 4 < line.length && line.substring(currentIndex, currentIndex + 4)
                        .equals("mul(")
                ) {
                    currentIndex += 4
                    val (leftNumber, lastIndex) = readNumber(
                        line.substring(
                            currentIndex,
                            line.length
                        )
                    )
                    currentIndex += lastIndex
                    if (line[currentIndex] == ',') {
                        currentIndex++
                        val (rightNumber, lastIndex) = readNumber(
                            line.substring(
                                currentIndex,
                                line.length
                            )
                        )
                        currentIndex += lastIndex
                        if (line[currentIndex] == ')') {
                            mulValue += leftNumber * rightNumber
                        }
                    }
                }
                currentIndex++
            }
        }
        return mulValue
    }

    fun part2(input: List<String>): Int {
        var mulValue = 0
        var enableMul = true
        val enabler = "do()"
        val disabler = "don't()"
        for (line in input) {
            var currentIndex = 0
            while (currentIndex < line.length) {
                if (currentIndex + disabler.length < line.length && line.substring(
                        currentIndex,
                        currentIndex + disabler.length
                    ) == disabler
                ) {
                    enableMul = false
                    currentIndex += disabler.length
                    continue
                }
                if (currentIndex + enabler.length < line.length && line.substring(
                        currentIndex,
                        currentIndex + enabler.length
                    ) == "do()"
                ) {
                    enableMul = true
                    currentIndex += enabler.length
                    continue
                }
                if (enableMul && currentIndex + 4 < line.length && line.substring(
                        currentIndex,
                        currentIndex + 4
                    ) == "mul("
                ) {
                    currentIndex += 4
                    val (leftNumber, lastLeftIndex) = readNumber(
                        line.substring(
                            currentIndex,
                            line.length
                        )
                    )
                    currentIndex += lastLeftIndex
                    if (line[currentIndex] == ',') {
                        currentIndex++
                        val (rightNumber, lastRightIndex) = readNumber(
                            line.substring(
                                currentIndex,
                                line.length
                            )
                        )
                        currentIndex += lastRightIndex
                        if (line[currentIndex] == ')') {
                            mulValue += leftNumber * rightNumber
                        }
                    }
                }
                currentIndex++
            }
        }
        return mulValue
    }

    check(part1(listOf("mul(3,5)")) == 15)
    check(part2(listOf("don't()mul(3,5)")) == 0)
    check(part2(listOf("don't()mul(3,5)do()mul(2,4)")) == 8)
    check(part2(listOf("don't()mul(3,5)do()mul(2,4)")) == 8)
    check(part2(listOf("don't()mul(3,5)do()mul(2x,4)")) == 0)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
