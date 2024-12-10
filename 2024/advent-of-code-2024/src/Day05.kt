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
                //entering here means the update needs a fix
                val fixedPages = mutableListOf<Int>() // list we need to fill with the right order
                for(page in line) { //iterate over all elements of the list
                    var found = false //flag to check if we found the right place to insert the element
                    // iterate the fixedPages list, from the end to the start. So if there's a rule for any already added element in our final list saying that the one
                    // we want to add need to be after, we can insert it right after that one.
                    for (i in fixedPages.size-1 downTo 0) {
                        //Example: if we want to add "2" and "2" needs to be after "1" and "1" is already in the list, we can insert "2" right after "1"
                        if (pagesOrder.containsKey(fixedPages[i]) && pagesOrder[fixedPages[i]]!!.contains(page)) {
                            fixedPages.add(i+1, page)
                            found = true
                            break
                        }
                    }
                    if(!found) { //if we didn't find any rule that says where to insert the element, we can just add it to the start of the list.
                        fixedPages.add(0, page)
                    }
                    if (fixedPages.size == 0) { //this is just for the first element, the fixedPages list is empty so we can just add it.
                        fixedPages.add(page)
                    }
                }
                total += fixedPages[(fixedPages.size /2)] //after orderning the line get the middle value and add to the total
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
