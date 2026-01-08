package store.model

data class ProcessResult(
    val appliedPromo: Promotion, val applyCount: Int, val product: Product
)