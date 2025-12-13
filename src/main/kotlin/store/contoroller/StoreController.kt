package store.contoroller

import store.constants.ErrorMessages
import store.model.PromotionResult
import store.model.Order
import store.model.OrderResult
import store.model.Parser
import store.model.Product
import store.model.Storage
import store.model.Store.checkPromo
import store.view.InputView
import store.view.OutputView

class StoreController {
    fun run() {
        OutputView.showWelcome()

        val storage = Storage.products

        while (true) {
            val orderResults = shopping(initialStorage = storage)
            orderResults.forEach { orderResult ->
                val product = storage.find { shelfProduct -> (shelfProduct == orderResult.product) }
                product!!.count = product.count - orderResult.totalCount
            }
            OutputView.showBuyAnotherGuide()
            val buyAnotherYn = InputView.readLine()
            if (buyAnotherYn == "N") {
                break
            }
        }


    }

    fun shopping(initialStorage: List<Product>): List<OrderResult> {
        var storage = initialStorage

        OutputView.showStorage(storage)
        OutputView.showShoppingGuide()

        val orders = Parser.orderParse(InputView.readLine())
        val orderResults = processOrder(orders = orders, storage = storage)
        val membershipYn: Boolean = checkMembership()

        var totalCount = 0
        var nonPromoPrice = 0
        var promoPrice = 0
        var totalPrice = 0
        var promoDiscount = 0
        var membershipDiscount = 0.0
        orderResults.filter { it.product.promotion != null }.forEach {
            promoPrice += (it.totalCount - it.promoCount) * it.product.price
            promoDiscount += it.promoCount * it.product.price
        }
        orderResults.filter { it.product.promotion == null }.forEach {
            nonPromoPrice += (it.totalCount - it.promoCount) * it.product.price
        }
        orderResults.forEach {
            totalPrice += (it.totalCount) * it.product.price
            totalCount += it.totalCount - it.promoCount
        }

        if (membershipYn) {
            membershipDiscount = nonPromoPrice * 0.3
        }

        OutputView.showReceipt(
            orderResults = orderResults,
            totalCount = totalCount,
            totalPrice = totalPrice,
            nonPromoPrice = nonPromoPrice,
            promoPrice = promoPrice,
            promoDiscount = promoDiscount,
            membershipDiscount = membershipDiscount,
        )
        return orderResults
    }

    fun processOrder(orders: List<Order>, storage: List<Product>): List<OrderResult> {
        val orderResults = mutableListOf<OrderResult>()
        for (order in orders) {
            val targetProducts = storage.filter { product -> product.name == order.productName }
            if (targetProducts.isEmpty()) {
                throw NoSuchElementException("상품명과 일치하는 상품이 없습니다.");
            }

            if (targetProducts.size > 1) {
                val processingResults: List<OrderResult> = promoProcess(targetProducts, order)

                orderResults.addAll(processingResults)
            } else {
                val processingResult: OrderResult = regularProcess(targetProducts.first(), order)

                orderResults.add(processingResult)
            }
        }
        return orderResults

    }

    fun promoProcess(targetProducts: List<Product>, order: Order): List<OrderResult> {
        when (val result: PromotionResult = checkPromo(targetProducts, order)) {
            is PromotionResult.Promotional -> {
                OutputView.showPromotional(result.product.name, result.promotion.get)
                val promotionYn = InputView.readLine()
                if (promotionYn == "Y") {
                    order.count += result.promotion.get
                }
                return listOf(
                    OrderResult(
                        product = result.product,
                        totalCount = order.count,
                        promoCount = (order.count / (result.promotion.buy + result.promotion.get)) * result.promotion.get
                    )
                )
            }

            is PromotionResult.NonPromotional -> {
                OutputView.showNonPromotional(result.promoProduct.name, result.nonPromotional)
                val nonPromotionYn = InputView.readLine()
                if (nonPromotionYn == "N") {
                    order.count = 0
                    return emptyList()
                }
                return listOf(
                    OrderResult(
                        product = result.promoProduct,
                        totalCount = order.count - result.nonPromotional,
                        promoCount = ((order.count - result.nonPromotional) / (result.promotion.buy + result.promotion.get)) * result.promotion.get
                    ),
                    OrderResult(
                        product = result.regularProduct,
                        totalCount = result.nonPromotional,
                        promoCount = 0
                    )
                )
            }

            is PromotionResult.Success -> {
                return listOf(
                    OrderResult(
                        product = result.product,
                        totalCount = order.count,
                        promoCount = (order.count / (result.promotion.buy + result.promotion.get)) * result.promotion.get
                    )
                )
            }
        }
    }

    fun regularProcess(targetProduct: Product, order: Order): OrderResult {
        require(order.count <= targetProduct.count) { ErrorMessages.OVER_PRODUCT_STOCK }
        return OrderResult(
            product = targetProduct,
            totalCount = order.count,
            promoCount = 0
        )
    }

    fun checkMembership(): Boolean {
        OutputView.showMemberShip()
        val memberShipYn = InputView.readLine()

        return memberShipYn == "Y"
    }

}