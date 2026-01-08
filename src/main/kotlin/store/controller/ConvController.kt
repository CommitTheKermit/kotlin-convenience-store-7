package store.controller

import store.Parser
import store.Validator
import store.constants.PromoProcessStatus
import store.model.Product
import store.model.Promotion
import store.service.ConvService
import store.service.FileService
import store.view.InputView
import store.view.OutputView

class ConvController {

    var promos: List<Promotion> = FileService.promoFileRead()
    var products: List<Product> = FileService.productFileRead(promos = promos)
    val convService = ConvService(
        products = products
    )

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

            var amount = 0
            orders.forEach { order ->
                val status: PromoProcessStatus = convService.processOrder(
                    order = order
                )

                when (status) {
                    PromoProcessStatus.NORMAL -> {
                        amount += convService.processPromo(
                            order = order
                        )
                    }

                    PromoProcessStatus.APPLICABLE -> {

                    }
                    PromoProcessStatus.INSUFFICIENT -> TODO()
                }
            }


        } catch (e: IllegalArgumentException) {
            OutputView.showError(e.message ?: "")
        }
    }


}