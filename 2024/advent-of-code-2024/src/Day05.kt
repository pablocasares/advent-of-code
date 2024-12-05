import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val pagesOrder = mutableMapOf<Int, MutableList<Int>>()
        val pages = mutableListOf<MutableList<Int>>()
        var readPages = false
        for (line in input) {
            if (line.isEmpty()) {
                readPages = true
                continue
            }
            if (readPages) {
                val page = line.split(",").map { it.toInt() }.toMutableList()
                pages.add(page)
            } else {
                val order = line.split("|").map { it.toInt() }.toMutableList()
                pagesOrder.computeIfAbsent(order[0]) { mutableListOf() }.add(order[1])
            }
        }

        var total = 0
        for (line in pages) {
            val pagesAdded = mutableSetOf<Int>()
            var isValid = true
            for(page in line) {
                if(pagesOrder.containsKey(page)) {
                    for (pageRule in pagesOrder[page]!!){
                        if(pagesAdded.contains(pageRule)) {
                            isValid = false
                            break
                        }
                    }
                }
                pagesAdded.add(page)
                if(!isValid) {
                    break
                }
            }

            if (isValid) {
                total += line[(line.size /2)]
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {

        val pagesOrder = mutableMapOf<Int, MutableList<Int>>()
        val pages = mutableListOf<MutableList<Int>>()
        var readPages = false
        for (line in input) {
            if (line.isEmpty()) {
                readPages = true
                continue
            }
            if (readPages) {
                val page = line.split(",").map { it.toInt() }.toMutableList()
                pages.add(page)
            } else {
                val order = line.split("|").map { it.toInt() }.toMutableList()
                pagesOrder.computeIfAbsent(order[0]) { mutableListOf() }.add(order[1])
            }
        }

        var total = 0
        for (line in pages) {
            val pagesAdded = mutableSetOf<Int>()
            var isValid = true
            for(page in line) {
                if(pagesOrder.containsKey(page)) {
                    for (pageRule in pagesOrder[page]!!){
                        if(pagesAdded.contains(pageRule)) {
                            isValid = false
                            break
                        }
                    }
                }
                pagesAdded.add(page)
                if(!isValid) {
                    break
                }
            }

            if (!isValid) {
                val fixedPages = mutableListOf<Int>()
                for(page in line) {
                    var found = false
                    for (i in fixedPages.size-1 downTo 0) {
                        if (pagesOrder.containsKey(fixedPages[i]) && pagesOrder[fixedPages[i]]!!.contains(page)) {
                            fixedPages.add(i+1, page)
                            found = true
                            break
                        }
                    }
                    if(!found) {
                        fixedPages.add(0, page)
                    }
                    if (fixedPages.size == 0) {
                        fixedPages.add(page)
                    }
                }
                total += fixedPages[(fixedPages.size /2)]
            }
        }
        return total
    }

//    check(part1(listOf("1|2","","2,1,3")) == 0)
//    check(part1(listOf("1|2","","1,2,3")) == 2)
//
//    check(part2(listOf("1|2","2|3","","3,1,2")) == 2)
    check(part2(listOf("1|2","2|3","2|4","3|5","3|4","4|5","","3,1,2,5,4")) == 3)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
