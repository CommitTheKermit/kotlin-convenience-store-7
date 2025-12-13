package store.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val count: Int = 0,
    val promotion: Promotion?
) {


    override fun toString(): String {
        if (promotion == null) {
            return "- $name ${price}원 ${count}개"
        }

        return "- $name ${price}원 ${count}개 $promotion"
    }
}