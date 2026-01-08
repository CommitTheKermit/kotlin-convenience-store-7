package store.service

import store.constants.PromoProcessStatus
import store.model.Order
import store.model.Product

class ConvService(val products: List<Product>) {
    fun processOrder(
        order: Order
    ): Pair<PromoProcessStatus, Int> {
        var amount = 0

        val promoProduct = products.find { product -> product.name == order.name && product.promotion != null }
        val regularProduct = products.find { product -> product.name == order.name && product.promotion == null }!!

        if (promoProduct != null) {
            val promo = promoProduct.promotion!!
            val total = promo.buy + promo.get
            if (promoProduct.quantity >= order.quantity && promoProduct.quantity % total == 0) {
                return Pair(PromoProcessStatus.PROMO_NORMAL, 0)
            }

            if (order.quantity % total == promo.buy) {
                Pair(PromoProcessStatus.APPLICABLE, 0)
            }

            if (promoProduct.quantity < order.quantity) {
                val insufficient = order.quantity - promoProduct.quantity
                return Pair(PromoProcessStatus.INSUFFICIENT, insufficient)
            }
        }

        return Pair(PromoProcessStatus.NORMAL, 0)
    }

    fun processPromo(order: Order, status: PromoProcessStatus): Int {
        var amount = 0
        val promoProduct = products.find { product -> product.name == order.name && product.promotion != null }!!

        val promo = promoProduct.promotion!!
        val total = promo.buy + promo.get

        if (status == PromoProcessStatus.APPLICABLE) {
            val newQuantity = (order.quantity / total) + total
            order.quantity = newQuantity
        }
        amount = ((order.quantity / total * promo.buy) + order.quantity % total) * promoProduct.price

        promoProduct.quantity -= order.quantity

        return amount
    }

    fun processNormal(order: Order, status: PromoProcessStatus): Int {
        var amount = 0
        val regularProduct = products.find { product -> product.name == order.name && product.promotion == null }!!

        amount = order.quantity * regularProduct.price

        regularProduct.quantity -= order.quantity

        return amount
    }

}