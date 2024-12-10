fun main() {
    fun part1(input: List<String>): String {
        val diskMap = input[0].toCharArray().map { it.toString().toInt() }

        val expandedDisk: MutableList<Int?> = mutableListOf()

        var fileId = 0
        for (i in 0 until diskMap.size) {
            if (i % 2 == 0) {
                for (j in 0 until diskMap[i]) {
                    expandedDisk.add(fileId)
                }
                fileId++

            } else {
                for (j in 0 until diskMap[i]) {
                    expandedDisk.add(null)
                }
            }
        }

        var i = 0
        var j = expandedDisk.size - 1

        while (i < j) {
            if (expandedDisk[i] != null) i++
            if (expandedDisk[j] == null) j--

            if (expandedDisk[i] == null && expandedDisk[j] != null) {
                expandedDisk[i] = expandedDisk[j]
                expandedDisk[j] = null
                i++
                j--
            }
        }

        var checksum = calculateChecksumByPosition(expandedDisk)

        return checksum
    }

    data class File(val id: Int?, val size: Int)

    fun part2(input: List<String>): String {
        val diskMap = input[0].toCharArray().map { it.toString().toInt() }

        val expandedDisk: MutableList<File> = mutableListOf()

        var fileId = 0
        for (i in 0 until diskMap.size) {
            if (i % 2 == 0) {
                expandedDisk.add(File(fileId, diskMap[i]))
                fileId++
            } else {
                expandedDisk.add(File(null, diskMap[i]))
            }
        }

        var j = expandedDisk.size - 1
        while (j >= 0) {
            if (expandedDisk[j].id == null) {
                j--
                continue
            }
            var i = 0
            while (i < j) {
                if (expandedDisk[i].id == null && expandedDisk[i].size > expandedDisk[j].size) {
                    val toMove = expandedDisk[j]
                    expandedDisk.add(i, toMove)
                    expandedDisk[i + 1] =
                        File(null, expandedDisk[i + 1].size - toMove.size)
                    expandedDisk[j+1] = File(null, expandedDisk[j+1].size)
                    break
                } else if (expandedDisk[i].id == null && expandedDisk[i].size == expandedDisk[j].size) {
                    val toMove = expandedDisk[j]
                    expandedDisk[i] = toMove
                    expandedDisk[j] = File(null, toMove.size)
                    break
                }
                i++
            }
            j--
        }


        val expandedCompactedDisk: MutableList<Int?> = mutableListOf()

        for (i in 0 until expandedDisk.size) {
            if (expandedDisk[i].id != null) {
                for (j in 0 until expandedDisk[i].size) {
                    expandedCompactedDisk.add(expandedDisk[i].id)
                }

            } else {
                for (j in 0 until expandedDisk[i].size) {
                    expandedCompactedDisk.add(null)
                }
            }
        }

        var checksum = calculateChecksumByPosition(expandedCompactedDisk)

        return checksum
    }

    val input = readInput("Day09")

    check(part2(listOf("2333133121414131402")) == "2858")

    part1(input).println()
    part2(input).println()
}
