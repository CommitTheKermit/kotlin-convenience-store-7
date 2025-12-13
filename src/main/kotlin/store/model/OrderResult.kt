package store.model

data class OrderResult(val product: Product, val promotion: Promotion, val totalCount: Int, val promoCount: Int)