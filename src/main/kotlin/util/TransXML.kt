package util


import java.util.ArrayList

class TransXMLk {
    fun Rule1Apply(input: ArrayList<*>): ArrayList<*> {
        val out = ArrayList<String>()
        var counter = 0
        while (counter < input.size) {
            var parse = input[counter++].toString()
            while (containDesc(parse)) parse = cutted(parse)
            if (parse != "") out.add(parse)
        }
        return out
    }

    fun containDesc(input: String): Boolean {
        return if (input.indexOf("<?") != -1)
            true
        else
            false
    }

    fun getStartedPos(input: String): Int {
        return input.indexOf("<?")
    }

    fun getFinisherPos(input: String): Int {
        return input.indexOf("?>")
    }

    internal fun cutted(input: String): String {
        return input.substring(getFinisherPos(input) + 2)
    }

    fun Rule1ApplyRemoveSpaces(input: ArrayList<*>): ArrayList<*> {
        val out = ArrayList<String>()
        var counter = 0
        while (counter < input.size) {
            var parse = input[counter++].toString()
            var clearedInfo = ""
            while (getOpenPos(parse) > 0) {
                parse = CutAndReturnCutted(parse, getOpenPos(parse), getClosedPos(parse))[1].toString()
                println("parse=$parse")
                clearedInfo += CutAndReturnCutted(parse, getOpenPos(parse), getClosedPos(parse))[0].toString()
                println("clear=$clearedInfo")
            }
            println(clearedInfo)
            out.add(clearedInfo)
        }
        return out
    }

    fun CutAndReturnCutted(input: String, cut1: Int, cut2: Int): ArrayList<*> {
        val result = ArrayList<String>()
        val resultStr = input.substring(cut1, cut2 + 1)
        val inputMod = input.substring(0, cut1) + input.substring(cut2 + 1, input.length)
        result.add(resultStr)
        result.add(inputMod)
        return result
    }

    fun getOpenPos(input: String): Int {
        return input.indexOf("<")
    }

    fun getClosedPos(input: String): Int {
        return input.indexOf(">")
    }

    fun removefloatSpaces(input: ArrayList<*>): ArrayList<*> {
        val result = ArrayList<String>()
        for (i in input.indices) result.add(removefloatSpaces(input[i].toString()))
        return result

    }

    fun removefloatSpaces(input: String): String {
        var process = input
        var k = 0
        var len = process.length
        while (k < len) {
            val pos = process.indexOf(' ')
            if (pos == 0) {
                process = process.substring(1)
                k++
                continue
            }
            var openedFlag = false
            var removeIt = false
            for (i in pos downTo 1) {
                if (process[i] == '>' && openedFlag == false) {
                    removeIt = true
                    break
                }
                if (process[i] == '<') {
                    openedFlag = true
                    break
                }
            }
            if (removeIt) process = process.substring(0, pos) + process.substring(pos + 1, process.length)
            len = process.length
            k++
        }
        return process
    }

    fun removeBackSlash(input: String): String {
        val pos = input.indexOf("/>")
        if (pos == -1) return input
        var tagName = ""
        val startTag = false
        for (i in pos downTo 1) {
            if (input[i] == '<') {
                var tracer = i + 1
                while (!startTag && input[tracer] != ' ') tagName += input[tracer++]
            }
        }
        return input
    }


}