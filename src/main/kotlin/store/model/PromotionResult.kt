package store.model

sealed class PromotionResult {
    data class Promotional(val product: Product, val promotion: Promotion) : PromotionResult()
    data class NonPromotional(val product: Product, val promotion: Promotion, val nonPromotional: Int) :
        PromotionResult()
    data class Success(val product: Product, val promotion: Promotion) : PromotionResult()
}