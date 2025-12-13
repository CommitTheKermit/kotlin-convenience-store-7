package store.view

import camp.nextstep.edu.missionutils.Console

object InputView {
    fun readLine(): String {
        val input = Console.readLine()
        println()
        return input ?: ""
    }


}