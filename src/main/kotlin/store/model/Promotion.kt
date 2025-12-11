package store.model

class Promotion(val name: String = "", val productId: Int) {

    override fun toString(): String {
        if (name == "null") {
            return ""
        }
        return name
    }
}