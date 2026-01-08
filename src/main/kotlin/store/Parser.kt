package store

import store.model.Order

object Parser {
    fun productInputParse(input: String): MutableList<Order> {
        val orders = mutableListOf<Order>()
        val productSplit = input.split(",")

        productSplit.forEach {
            require(it.first() == '[' && it.last() == ']') { "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요." }

            val raw = it.subSequence(1, it.length - 1)
            val split = raw.split("-")
            require(split.size > 1) { "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요." }

            val order = Order(
                name = split[0],
                quantity = split[1].toInt()
            )
            orders.add(order)
        }

        return orders
    }
}