package store.model

import store.constants.ErrorMessages
import store.view.OutputView

object Store {
    fun processOrder(orders: List<Order>, storage: List<Product>) {
        orders.forEach { order ->
            val targetProducts =
                storage.filter { product -> product.name == order.productName }
            if (targetProducts.isEmpty()) {
                throw NoSuchElementException("상품명과 일치하는 상품이 없습니다.");
            }

            if (targetProducts.size > 1) {
                promoOrderProcess(targetProducts, order)

            } else {
                orderProcess()
            }



        }
    }

    fun promoOrderProcess(targetProducts: List<Product>, order: Order) {
        val (promoProducts, products) = targetProducts.partition { it.promotion != null }
        val sortedProducts = promoProducts + products

        val promoProduct = promoProducts.first()
        val product = products.first()

        val productCount = promoProduct.count + product.count

        require(order.count < productCount) { ErrorMessages.OVER_PRODUCT_STOCK }

        val promotion: Promotion = promoProduct.promotion!!
        val buyGetTotalCount = (promotion.buy + promotion.get)
//            프로모션 적용 가능 안내 문구 출력
        if (order.count == promotion.buy && promoProduct.count >= (promotion.buy + promotion.get)
        ) {
            OutputView.showPromotional(product.name, promotion.get)
        }
//            프로모션 적용 불가능 수량 안내 문구 출력
        if (promoProduct.count < order.count) {
            val nonPromotionalCount: Int =
                order.count - buyGetTotalCount * (promoProduct.count / buyGetTotalCount)

            OutputView.showNonPromotional(product.name, nonPromotionalCount)
        }
    }

    fun orderProcess() {}


}