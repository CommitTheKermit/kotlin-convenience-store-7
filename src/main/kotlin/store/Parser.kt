package store

import store.model.Order
import store.model.Product

object Parser {
    fun productInputParse(input: String, products: List<Product>): MutableList<Order> {
        val orders = mutableListOf<Order>()
        val productSplit = input.split(",")

        productSplit.forEach {
            require(it.first() == '[' && it.last() == ']') { "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요." }

            val raw = it.subSequence(1, it.length - 1)
            val split = raw.split("-")
            require(split.size > 1) { "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요." }

            val targetProduct = products.find { product -> product.name == split[0] }
            require(targetProduct != null) { "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요." }

            val order = Order(
                product = targetProduct,
                quantity = split[1].toInt()
            )
            orders.add(order)
        }

        return orders
    }

    fun parseYN(input: String): Boolean {
        require(listOf("Y", "N", "y", "n").contains(input)) { "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요." }

        return listOf("Y", "y").contains(input)

    }
}