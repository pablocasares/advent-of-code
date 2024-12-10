import java.math.BigInteger

fun main() {

    fun findObjectiveWithSumAndMul(
        parts: MutableList<BigInteger>,
        result: BigInteger,
        acc: BigInteger
    ): Boolean {
        if (parts.size == 0) return result == acc
        val found =
            findObjectiveWithSumAndMul(parts.drop(1).toMutableList(), result, acc + parts[0]) ||
                    findObjectiveWithSumAndMul(
                        parts.drop(1).toMutableList(),
                        result,
                        acc * parts[0]
                    )
        return found
    }

    fun findObjectiveWithSumMulAndConc(
        parts: MutableList<BigInteger>,
        result: BigInteger,
        acc: BigInteger
    ): Boolean {
        if (parts.size == 0) return result == acc
        val found =
            findObjectiveWithSumMulAndConc(parts.drop(1).toMutableList(), result, acc + parts[0]) ||
                    findObjectiveWithSumMulAndConc(
                        parts.drop(1).toMutableList(),
                        result,
                        acc * parts[0]
                    )
                    || findObjectiveWithSumMulAndConc(
                parts.drop(1).toMutableList(),
                result,
                BigInteger(acc.toString() + parts[0])
            )
        return found
    }

    fun part1(input: List<String>): BigInteger {
        var total = BigInteger("0")

        for (line in input) {
            val result = line.split(":")[0].toBigInteger()
            val parts =
                line.split(":")[1].trim().split(" ").map(String::toBigInteger).toMutableList()

            if (findObjectiveWithSumAndMul(parts, result, BigInteger("0"))) {
                total += result
            }

        }
        return total
    }

    fun part2(input: List<String>): BigInteger {
        var total = BigInteger("0")

        for (line in input) {
            val result = line.split(":")[0].toBigInteger()
            val parts =
                line.split(":")[1].trim().split(" ").map(String::toBigInteger).toMutableList()

            if (findObjectiveWithSumMulAndConc(parts, result, BigInteger("0"))) {
                total += result
            }

        }
        return total
    }


    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
