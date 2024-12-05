import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val inputMatrix = input.map { it.toCharArray() }

        fun readXMas(i: Int, j: Int, moveFn: (Int, Int) -> Pair<Int, Int>): Boolean {
            var charactersMatch = 0
            val lookup = "XMAS"

            var newI = i
            var newJ = j
            for (numMove in lookup.indices) {
                if (newI < 0 || newI >= inputMatrix.size || newJ < 0 || newJ >= inputMatrix[0].size) {
                    break
                }
                if (inputMatrix[newI][newJ] == lookup[numMove]) {
                    charactersMatch++
                }
                val newIndexes = moveFn(newI, newJ)
                newI = newIndexes.first
                newJ = newIndexes.second
            }

            return charactersMatch == lookup.length
        }

        var totalFound = 0
        for(i in inputMatrix.indices) {
            for(j in inputMatrix[0].indices) {
                println(inputMatrix[i][j])
                if(inputMatrix[i][j] == 'X') {
                    if(readXMas(i, j, {i, j -> i to j + 1})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i to j - 1})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i + 1 to j})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i - 1 to j})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i + 1 to j + 1})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i + 1 to j - 1})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i - 1 to j + 1})) {
                        totalFound++
                    }
                    if(readXMas(i, j, {i, j -> i - 1 to j - 1})) {
                        totalFound++
                    }
                }
            }
        }

        return totalFound
    }

    fun part2(input: List<String>): Int {
        val inputMatrix = input.map { it.toCharArray() }


        var totalFound = 0
        for(i in 1 until inputMatrix.size - 1) {
            for(j in 1 until inputMatrix[0].size - 1) {
                println(inputMatrix[i][j])
                if(inputMatrix[i][j] == 'A') {

                    var lookup = mutableSetOf('M', 'S')

                    val cross1First = i-1 to j-1
                    val cross1Second = i+1 to j+1

                    val cross2First = i-1 to j+1
                    val cross2Second = i+1 to j-1

                    if(lookup.contains(inputMatrix[cross1First.first][cross1First.second])) {
                        lookup.remove(inputMatrix[cross1First.first][cross1First.second])
                    }

                    if(lookup.contains(inputMatrix[cross1Second.first][cross1Second.second])) {
                        lookup.remove(inputMatrix[cross1Second.first][cross1Second.second])
                    }

                    if(lookup.size != 0) {
                        continue
                    }

                    lookup = mutableSetOf('M', 'S')

                    if(lookup.contains(inputMatrix[cross2First.first][cross2First.second])) {
                        lookup.remove(inputMatrix[cross2First.first][cross2First.second])
                    }

                    if(lookup.contains(inputMatrix[cross2Second.first][cross2Second.second])) {
                        lookup.remove(inputMatrix[cross2Second.first][cross2Second.second])
                    }

                    if(lookup.size != 0) {
                        continue
                    }
                    totalFound++
                }
            }
        }

        return totalFound
    }

    check(part1(listOf("XMAS")) == 1)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
