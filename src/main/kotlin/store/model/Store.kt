package store.model

import store.constants.ErrorMessages

object Store {
    fun checkPromo(targetProducts: List<Product>, order: Order): PromotionResult {
        val (promoProducts, products) = targetProducts.partition { it.promotion != null }

        val promoProduct = promoProducts.first()
        val product = products.first()

        val productCount = promoProduct.count + product.count

        require(order.count <= productCount) { ErrorMessages.OVER_PRODUCT_STOCK }

        val promotion: Promotion = promoProduct.promotion!!
        val buyGetTotalCount = (promotion.buy + promotion.get)
//            프로모션 적용 가능 안내 문구 출력
        if (order.count == promotion.buy && promoProduct.count >= (promotion.buy + promotion.get)
        ) {
            return PromotionResult.Promotional(
                product = promoProduct,
                promotion = promotion,
            )
        }
//            프로모션 적용 불가능 수량 안내 문구 출력
        if (promoProduct.count <= order.count) {
            val nonPromotionalCount: Int =
                order.count - buyGetTotalCount * (promoProduct.count / buyGetTotalCount)
            return PromotionResult.NonPromotional(
                promoProduct,
                product,
                promotion,
                nonPromotionalCount
            )
        }
        return PromotionResult.Success(
            product = promoProduct,
            promotion = promotion,
        )
    }

    fun orderProcess() {}

    fun membership() {

    }

    fun checkPromotional() {

    }


}
