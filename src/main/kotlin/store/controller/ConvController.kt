package store.controller

import store.model.Product
import store.model.Promotion
import store.service.FileService
import store.view.OutputView

class ConvController {

    var promos: List<Promotion> = FileService.promoFileRead()
    var products: List<Product> = FileService.productFileRead(promos = promos)
    fun run() {
        OutputView.showStartGuide()

        OutputView.showProduct(products = products)
    }


}