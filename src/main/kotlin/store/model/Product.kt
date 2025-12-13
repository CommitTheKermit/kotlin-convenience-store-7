package store.model

data class Product(
    val name: String = "",
    val price: Int = 0,
    val count: Int = 0,
    val promotion: Promotion?
)