import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val left = input.map { it.split("   ")[0] }.sorted()
        val right = input.map { it.split("   ")[1] }.sorted()

        var distance = 0
        for (i in 0 until left.size) {
            distance += abs(left[i].toInt() - right[i].toInt())
        }
        return distance
    }

    fun part2(input: List<String>): Int {
        val left = input.map { it.split("   ")[0].toInt() }.sorted()
        val right = input.map { it.split("   ")[1].toInt() }.groupingBy { it }.eachCount()

        var similarity = 0
        for (i in 0 until left.size) {
            similarity += left[i]*right.getOrElse(left[i]) { 0 }
        }
        return similarity
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    //val testInput = readInput("Day01")
    //check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
