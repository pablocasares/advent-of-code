import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val inputMatrix =
            input.toMutableList().map { it.toCharArray().toMutableList() }.toMutableList()
        val startPosition = findCharacterPositionInMatrix(inputMatrix, '^')
        val movements = listOf(Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT)
        val nextMovement =
            { movement: Movement -> movements[(movements.indexOf(movement) + 1) % movements.size] }
        val total = traverseMatrixWithDirection(
            inputMatrix,
            startPosition!!.first,
            startPosition.second,
            Movement.UP,
            nextMovement
        )
        return total
    }

    fun part2(input: List<String>): Int {
        val originalMatrix =
            input.map { it.toCharArray().toList() }
        val startPosition = findCharacterPositionInMatrix(originalMatrix, '^')
        val movements = listOf(Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT)
        val nextMovement =
            { movement: Movement -> movements[(movements.indexOf(movement) + 1) % movements.size] }
        val pointsVisited = traverseMatrixAndReturnAllPositions(
            originalMatrix,
            startPosition!!.first,
            startPosition.second,
            Movement.UP,
            nextMovement
        )
        var totalLoops = 0

        for (pointVisited in pointsVisited) {
            val i = pointVisited.first
            val j = pointVisited.second
            val mutableMatrix =
                originalMatrix.toMutableList().map { it.toMutableList() }.toMutableList()
            mutableMatrix[i][j] = '#'
            if (detectMatrixLoop(
                    mutableMatrix, startPosition!!.first,
                    startPosition.second, Movement.UP, nextMovement
                )
            ) {
                totalLoops++
            }
        }

        return totalLoops
    }


    check(part1(listOf("...", "...", ".^.")) == 3)
    check(part1(listOf(".#.", "...", ".^.")) == 3)

    check(
        part2(
            listOf(
                "....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#..."
            )
        ) == 6
    )


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
