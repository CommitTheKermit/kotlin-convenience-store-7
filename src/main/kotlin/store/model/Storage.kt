package store.model

import java.io.File
import java.sql.Date

object Storage {
    val promotions: List<Promotion>
    val products: List<Product>

    init {
        promotions = initPromotion()
        products = initStorage()
    }

    fun initStorage(): List<Product> {
        val processingProducts = mutableListOf<Product>()
        val lines = fileRead("src/main/resources/products.md")


        lines.forEach { item ->
            val split = item.split(',')
            val product = Product(
                name = split[0],
                price = split[1].toInt(),
                count = split[2].toInt(),
                promotion = promotions.find { promotion -> promotion.name == split[3] },
            )
            processingProducts.add(product)
        }
        return processingProducts
    }

    fun initPromotion(): List<Promotion> {
        val processingPromotions = mutableListOf<Promotion>()
        val lines = fileRead("src/main/resources/promotions.md")


        lines.forEachIndexed { index, item ->
            val split = item.split(',')
            val promotion = Promotion(
                name = split[0],
                buy = split[1].toInt(),
                get = split[2].toInt(),
                startDate = Date.valueOf(split[3]),
                endDate = Date.valueOf(split[4]),
            )
            processingPromotions.add(promotion)
        }
        return processingPromotions;
    }

    fun fileRead(filePath: String): List<String> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalStateException("products.md 파일을 찾을 수 없습니다")
        }
        val lines = file.readLines().drop(1)

        return lines
    }


}