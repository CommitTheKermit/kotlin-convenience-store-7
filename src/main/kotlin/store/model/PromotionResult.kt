import store.model.Product
import store.model.Promotion

sealed class PromotionResult {
    data class Promotional(val product: Product, val promotion: Promotion) : PromotionResult()
    data class NonPromotional(val product: Product, val nonPromotional: Int) :
        PromotionResult()

    object Success : PromotionResult()
}