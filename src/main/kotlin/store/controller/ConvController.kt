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
                val result: Pair<PromoProcessStatus, Int> = convService.processOrder(
                    order = order
                )

                when (result.first) {
                    PromoProcessStatus.PROMO_NORMAL -> {
                        amount += convService.processPromo(
                            order = order, status = result.first
                        )
                    }

                    PromoProcessStatus.APPLICABLE -> {
                        OutputView.showPromoApplicable(order = order)
                        val yn = Parser.parseYN(InputView.readLine())

                        amount += if (yn) {
                            convService.processPromo(order = order, status = result.first)
                        } else {
                            convService.processPromo(order = order, status = PromoProcessStatus.PROMO_NORMAL)
                        }
                    }

                    PromoProcessStatus.INSUFFICIENT -> {
                        OutputView.showPromoInsufficient(
                            order = order,
                            insufficientCount = result.second
                        )
                        val yn = Parser.parseYN(InputView.readLine())
                        amount += if (yn) {
                            convService.processPromo(order = order, status = PromoProcessStatus.INSUFFICIENT)
                        } else {
                            0
                        }

                    }

                    PromoProcessStatus.NORMAL -> {
                        amount += convService.processNormal(
                            order = order,
                            status = PromoProcessStatus.NORMAL
                        )
                    }
                }
            }
            println(amount)

        } catch (e: IllegalArgumentException) {
            OutputView.showError(e.message ?: "")
        }
    }


}