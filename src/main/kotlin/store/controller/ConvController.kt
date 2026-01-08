package store.controller

import store.model.Product
import store.model.Promotion
import store.service.FileService

class ConvController {

    var promos: List<Promotion> = FileService.promoFileRead()
    var products: List<Product> = FileService.productFileRead(promos = promos)
    fun run() {
        println()
    }


}