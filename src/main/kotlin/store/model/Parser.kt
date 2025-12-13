package store.model

object Parser {
    fun orderParse(input: String): List<Order> {
        val orders = input.split(',')
        val parsedOrders = mutableListOf<Order>()
        orders.forEach {
            val order = it.substring(1, it.length - 1)
            val split = order.split('-')
            val parsedOrder = Order(productName = split[0], count = split[1].toInt())
            parsedOrders.add(parsedOrder)
        }

        return parsedOrders
    }
}