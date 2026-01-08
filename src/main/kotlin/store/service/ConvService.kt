package store.service

import store.constants.PromoProcessStatus
import store.model.Order
import store.model.Product

object ConvService {
    fun processOrder(
        products: List<Product>, order: Order
    ): Int {
        var amount = 0

        val promoProduct = products.find { product -> product.name == order.name && product.promotion != null }
        val regularProduct = products.find { product -> product.name == order.name && product.promotion == null }!!

        if (promoProduct != null) {
            if (promoProduct.quantity >= order.quantity) {
                promoProduct.quantity -= order.quantity

                amount += processPromo(
                    promoProduct = promoProduct,
                    order = order
                )
                return amount
            }
        }



        return 0
    }

    fun processPromo(promoProduct: Product, order: Order): Int {
        var amount = 0
        val promo = promoProduct.promotion!!
        val total = promo.buy + promo.get
        amount = ((order.quantity / total * promo.buy) + order.quantity % total) * promoProduct.price

        return amount
    }

}