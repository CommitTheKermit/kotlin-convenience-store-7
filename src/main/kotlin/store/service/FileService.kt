package store.service

import store.model.Product
import store.model.Promotion
import java.io.File
import java.time.LocalDate

object FileService {
    fun promoFileRead(): MutableList<Promotion> {
        val promos = mutableListOf<Promotion>()

        val file = File("src/main/resources/promotions.md")
        val lines = file.readLines().drop(1)

        lines.forEach { line ->
            val split: List<String> = line.split(",")

            val name = split[0]
            val buy = split[1].toInt()
            val get = split[2].toInt()
            val startDate = LocalDate.parse(split[3])
            val endDate = LocalDate.parse(split[4])

            val promo = Promotion(
                name = name,
                buy = buy,
                get = get,
                startDate = startDate,
                endDate = endDate
            )
            promos.add(promo)
        }
        return promos
    }

    fun productFileRead(promos: List<Promotion>): MutableList<Product> {
        val products = mutableListOf<Product>()

        val file = File("src/main/resources/products.md")
        val lines = file.readLines().drop(1)

        lines.forEach { line ->
            val split: List<String> = line.split(",")

            val name = split[0]
            val price = split[1].toInt()
            val quantity = split[2].toInt()
            val promotion = promos.find { it.name == split[3] }

            val product = Product(
                name = name,
                price = price,
                quantity = quantity,
                promotion = promotion
            )
            products.add(product)
        }
        return products
    }

}