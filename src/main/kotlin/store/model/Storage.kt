package store.model

import java.io.File

object Storage {
    val products: List<Product>

    init {
        products = initStorage()
    }

    fun initStorage(): List<Product> {
        val processingProducts = mutableListOf<Product>()
        val file = File("src/main/resources/products.md")
        if (!file.exists()) {
            throw IllegalStateException("products.md 파일을 찾을 수 없습니다")
        }
        val lines = file.readLines().drop(1)


        lines.forEachIndexed { index, item ->
            val split = item.split(',')
            val product = Product(
                id = index,
                productName = split[0],
                price = split[1].toInt(),
                count = split[2].toInt(),
                promotion = Promotion(name = split[3], productId = index),
            )
            processingProducts.add(product)
        }
        return processingProducts
    }
}