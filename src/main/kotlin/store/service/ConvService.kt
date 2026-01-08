package store.service

import store.constants.PromoProcessStatus
import store.model.Order
import store.model.Product

class ConvService(val products: List<Product>) {
    fun processOrder(
        order: Order
    ): PromoProcessStatus {
        var amount = 0

        val promoProduct = products.find { product -> product.name == order.name && product.promotion != null }
        val regularProduct = products.find { product -> product.name == order.name && product.promotion == null }!!

        if (promoProduct != null) {
            if (promoProduct.quantity >= order.quantity) {
                return PromoProcessStatus.NORMAL
            }
        }



        return PromoProcessStatus.NORMAL
    }

    fun processPromo(order: Order): Int {
        var amount = 0
        val promoProduct = products.find { product -> product.name == order.name && product.promotion != null }!!

        val promo = promoProduct.promotion!!
        val total = promo.buy + promo.get
        amount = ((order.quantity / total * promo.buy) + order.quantity % total) * promoProduct.price

        promoProduct.quantity -= order.quantity

        return amount
    }

}