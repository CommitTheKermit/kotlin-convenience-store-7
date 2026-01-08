package store.controller

import store.Parser
import store.Validator
import store.constants.PromoProcessStatus
import store.model.ProcessResult
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
        while (true) {
            try {
                OutputView.showStartGuide()

                OutputView.showProduct(products = products)

                OutputView.showProductInputGuide()
                val orders = Parser.productInputParse(
                    input = InputView.readLine(),
                    products = products
                )
                Validator.validateOrders(
                    products = products,
                    orders = orders
                )

                var amount = 0
                var promotionAmount = 0
                var processedResults = mutableListOf<ProcessResult>()
                orders.forEach { order ->
                    val result: Pair<PromoProcessStatus, Int> = convService.processOrder(
                        order = order
                    )

                    when (result.first) {
                        PromoProcessStatus.PROMO_NORMAL -> {
                            processedResults.add(
                                convService.processPromo(
                                    order = order, status = result.first
                                )
                            )
                        }

                        PromoProcessStatus.APPLICABLE -> {
                            OutputView.showPromoApplicable(order = order)
                            val yn = Parser.parseYN(InputView.readLine())

                            processedResults.add(
                                if (yn) {
                                    convService.processPromo(order = order, status = result.first)
                                } else {
                                    convService.processPromo(order = order, status = PromoProcessStatus.PROMO_NORMAL)
                                }
                            )
                        }

                        PromoProcessStatus.INSUFFICIENT -> {
                            OutputView.showPromoInsufficient(
                                order = order,
                                insufficientCount = result.second
                            )
                            val yn = Parser.parseYN(InputView.readLine())
                            if (yn) {
                                processedResults.add(
                                    convService.processPromo(order = order, status = PromoProcessStatus.INSUFFICIENT)
                                )

                            }
                        }

                        PromoProcessStatus.NORMAL -> {
                            amount += convService.processNormal(
                                order = order,
                            )
                        }
                    }
                }

                OutputView.showMembershipInputGuide()

                val totalAmount =
                    orders.sumOf { order -> order.quantity * (products.find { it.name == order.product.name })!!.price }
                val discountPrice = processedResults.sumOf { it.appliedPromo.get * it.applyCount * it.product.price }
                val totalPromoPrice =
                    processedResults.sumOf { (it.appliedPromo.buy + it.appliedPromo.get) * it.applyCount * it.product.price }
                var nonPromoAmount = totalAmount - totalPromoPrice
                val membershipYn = Parser.parseYN(InputView.readLine())
                var membershipDisCountAmount = 0
                if (membershipYn) {
                    membershipDisCountAmount = (nonPromoAmount * 0.3).toInt()
                    nonPromoAmount -= membershipDisCountAmount

                }

                OutputView.showReceipt(
                    orders = orders,
                    processedResults = processedResults,
                    totalAmount = totalAmount,
                    discountedAmount = discountPrice,
                    membershipDiscountAmount = membershipDisCountAmount,
                    actualPrice = nonPromoAmount,
                    totalQuantity = orders.sumOf { it.quantity }
                )

                OutputView.showRetryInputGuide()
                val retryYN = Parser.parseYN(InputView.readLine())
                if (!retryYN) {
                    return
                }
            } catch (e: IllegalArgumentException) {
                OutputView.showError(e.message ?: "")
            }
        }

    }


}