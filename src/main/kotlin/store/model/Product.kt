package store.model

class Product(
    val id: Int = 0,
    val productName: String = "",
    val price: Int = 0,
    val count: Int = 0,
    val promotion: Promotion
) {


    override fun toString(): String {
        if (promotion.name == "null") {
            return "- $productName ${price}원 ${count}개"
        }

        return "- $productName ${price}원 ${count}개 $promotion"
    }
}