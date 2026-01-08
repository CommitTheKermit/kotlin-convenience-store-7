package store

import store.model.Order
import store.model.Product

object Validator {
    fun validateOrders(products: List<Product>, orders: List<Order>) {
        orders.forEach { order ->

            val filtered = products.filter { product -> product.name == order.name }
            require(filtered.isNotEmpty()) { "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요." }
            val totalCount: Int = filtered.sumOf { product -> product.quantity }
            require(totalCount >= order.quantity) { "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요." }
        }
    }
}