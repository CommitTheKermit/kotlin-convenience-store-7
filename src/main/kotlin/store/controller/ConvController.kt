package store.controller

import store.Parser
import store.Validator
import store.model.Product
import store.model.Promotion
import store.service.FileService
import store.view.InputView
import store.view.OutputView

class ConvController {

    var promos: List<Promotion> = FileService.promoFileRead()
    var products: List<Product> = FileService.productFileRead(promos = promos)
    fun run() {
        try {
            OutputView.showStartGuide()

            OutputView.showProduct(products = products)

            OutputView.showProductInputGuide()
            val orders = Parser.productInputParse(InputView.readLine())
            Validator.validateOrders(
                products = products,
                orders = orders
            )
        } catch (e: IllegalArgumentException) {
            OutputView.showError(e.message ?: "")
        }
    }


}