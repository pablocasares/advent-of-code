import java.math.BigInteger
import java.security.MessageDigest
import java.util.Vector
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("inputs/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

enum class Movement(val movement: (i: Int, j: Int) -> Pair<Int, Int>) {
    RIGHT({ i, j -> i to j + 1 }), DOWN({ i, j -> i + 1 to j }), LEFT({ i, j -> i to j - 1 }), UP({ i, j -> i - 1 to j }), DOWN_RIGHT(
        { i, j -> i + 1 to j + 1 }),
    DOWN_LEFT({ i, j -> i + 1 to j - 1 }), UP_RIGHT({ i, j -> i - 1 to j + 1 }), UP_LEFT({ i, j -> i - 1 to j - 1 })
}

fun traverseMatrixWithDirection(
    matrix: MutableList<MutableList<Char>>,
    i: Int,
    j: Int,
    movement: Movement,
    nextMovement: (Movement) -> (Movement)
): Int {
    var i = i
    var j = j
    if (i < 0 || j < 0 || i >= matrix.size || j >= matrix[0].size) return 0
    var totalVisited = 0

    while (i >= 0 && i < matrix.size && j >= 0 && j < matrix[0].size) {
        if (matrix[i][j] != 'X') {
            totalVisited++
            matrix[i].set(j, 'X')
        }
        val (newI, newJ) = movement.movement(i, j)
        if (newI < 0 || newJ < 0 ||
            newI >= matrix.size || newJ >= matrix[0].size
        ) return totalVisited

        if (matrix[newI][newJ] == '#') {
            val nextDirection = nextMovement(movement)
            return totalVisited + traverseMatrixWithDirection(
                matrix,
                i,
                j,
                nextDirection,
                nextMovement
            )
        }
        i = newI
        j = newJ
    }
    return totalVisited
}

fun traverseMatrixAndReturnAllPositions(
    matrix: List<List<Char>>,
    i: Int,
    j: Int,
    movement: Movement,
    nextMovement: (Movement) -> (Movement)
): Set<Pair<Int, Int>> {
    var i = i
    var j = j
    val totalVisited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    if (i < 0 || j < 0 || i >= matrix.size || j >= matrix[0].size) return totalVisited

    while (i >= 0 && i < matrix.size && j >= 0 && j < matrix[0].size) {
        totalVisited.add(i to j)

        val (newI, newJ) = movement.movement(i, j)
        if (newI < 0 || newJ < 0 ||
            newI >= matrix.size || newJ >= matrix[0].size
        ) return totalVisited.toSet()

        if (matrix[newI][newJ] == '#') {
            val nextDirection = nextMovement(movement)
            totalVisited.addAll(
                traverseMatrixAndReturnAllPositions(
                    matrix,
                    i,
                    j,
                    nextDirection,
                    nextMovement
                )
            )
            return totalVisited.toSet()
        }
        i = newI
        j = newJ
    }
    return totalVisited.toSet()
}


fun detectMatrixLoop(
    matrix: List<List<Char>>,
    i: Int,
    j: Int,
    movement: Movement,
    nextMovement: (Movement) -> (Movement)
): Boolean {
    var i = i
    var j = j
    var movement = movement
    val visited = mutableSetOf<Triple<Int, Int, Movement>>()
    if (i < 0 || j < 0 || i >= matrix.size || j >= matrix[0].size) return false

    while (i >= 0 && i < matrix.size && j >= 0 && j < matrix[0].size) {
        if (visited.contains(Triple(i, j, movement))) {
            return true
        }
        visited.add(Triple(i, j, movement))

        val (newI, newJ) = movement.movement(i, j)
        if (newI < 0 || newJ < 0 ||
            newI >= matrix.size || newJ >= matrix[0].size
        ) return false

        if (matrix[newI][newJ] == '#') {
            movement = nextMovement(movement)
            continue
        }
        i = newI
        j = newJ
    }
    return false
}

fun findCharacterPositionInMatrix(matrix: List<List<Char>>, character: Char): Pair<Int, Int>? {
    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            if (matrix[i][j] == character) {
                return i to j
            }
        }
    }
    return null
}

fun getAllPairsFromList(list: List<Any>): List<Pair<Any, Any>> {
    val pairs = mutableListOf<Pair<Any, Any>>()
    for (i in list.indices) {
        for (j in i + 1 until list.size) {
            pairs.add(list[i] to list[j])
        }
    }
    return pairs
}

fun getAntiNodes(a: Pair<Int, Int>, b: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val firstAntiNode = a.first + (a.first - b.first) to a.second + (a.second - b.second)
    val secondAntiNode = b.first + (b.first - a.first) to b.second + (b.second - a.second)
    return firstAntiNode to secondAntiNode
}

fun getAntiNodesUntilOut(
    a: Pair<Int, Int>,
    b: Pair<Int, Int>,
    gridSize: Pair<Int, Int>
): List<Pair<Int, Int>> {
    val antinodes = mutableListOf<Pair<Int, Int>>()
    antinodes.add(a)
    antinodes.add(b)

    var position = a
    while (true) {
        val antinode =
            position.first + (a.first - b.first) to position.second + (a.second - b.second)
        if (antinode.first < 0 || antinode.second < 0 || antinode.first >= gridSize.first || antinode.second >= gridSize.second) {
            break
        }
        antinodes.add(antinode)
        position = antinode
    }

    position = b
    while (true) {
        val antinode =
            position.first + (b.first - a.first) to position.second + (b.second - a.second)
        if (antinode.first < 0 || antinode.second < 0 || antinode.first >= gridSize.first || antinode.second >= gridSize.second) {
            break
        }
        antinodes.add(antinode)
        position = antinode
    }

    return antinodes
}

fun calculateChecksumByPosition(values: List<Int?>): String {
    var checksum = BigInteger("0")
    for (i in 0 until values.size) {
        if (values[i] != null) {
            checksum += BigInteger(values[i].toString())*BigInteger(i.toString())
        }
    }
    return checksum.toString()
}
