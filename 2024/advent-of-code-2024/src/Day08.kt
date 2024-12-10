import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val groupsAntennas = mutableMapOf<Char, MutableSet<Pair<Int, Int>>>()
        for (i in input.indices) {
            val parts = input[i].toCharArray()
            for (j in input[0].indices) {
                val c = parts[j]
                if (c != '.') {
                    val antennas = groupsAntennas.computeIfAbsent(c) { mutableSetOf() }
                    antennas.add(i to j)
                }
            }
        }

        val uniqueLocations: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (groupAntenna in groupsAntennas) {
            val antennas = groupAntenna.value
            getAllPairsFromList(antennas.toList()).forEach {
                val nodes = it as Pair<Pair<Int, Int>, Pair<Int, Int>>
                val antinodes = getAntiNodes(nodes.first, nodes.second)
                if (antinodes.first.first >= 0 && antinodes.first.second >= 0 && antinodes.first.first < input.size && antinodes.first.second < input[0].length) {
                    uniqueLocations.add(antinodes.first)
                }
                if (antinodes.second.first >= 0 && antinodes.second.second >= 0 && antinodes.second.first < input.size && antinodes.second.second < input[0].length) {
                    uniqueLocations.add(antinodes.second)
                }
            }
        }

        return uniqueLocations.size
    }

    fun part2(input: List<String>): Int {
        val groupsAntennas = mutableMapOf<Char, MutableSet<Pair<Int, Int>>>()
        for (i in input.indices) {
            val parts = input[i].toCharArray()
            for (j in input[0].indices) {
                val c = parts[j]
                if (c != '.') {
                    val antennas = groupsAntennas.computeIfAbsent(c) { mutableSetOf() }
                    antennas.add(i to j)
                }
            }
        }

        val uniqueLocations: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (groupAntenna in groupsAntennas) {
            val antennas = groupAntenna.value
            getAllPairsFromList(antennas.toList()).forEach {
                val nodes = it as Pair<Pair<Int, Int>, Pair<Int, Int>>
                uniqueLocations.addAll(
                getAntiNodesUntilOut(
                    nodes.first,
                    nodes.second,
                    input.size to input[0].length
                ))
            }
        }

        return uniqueLocations.size
    }

    check(getAllPairsFromList(listOf(1, 2, 3)).size == 3)
    check(part1(listOf("....", ".a..", "..a.", "....")) == 2)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
