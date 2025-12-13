package store.contoroller

import PromotionResult
import store.model.Order
import store.model.Parser
import store.model.Product
import store.model.Storage
import store.model.Store.orderProcess
import store.model.Store.checkPromo
import store.view.InputView
import store.view.OutputView

class StoreController {
    fun run() {
        OutputView.showWelcome()

        val storage = Storage.products

        shopping(initialStorage = storage)

    }

    fun shopping(initialStorage: List<Product>) {
        var storage = initialStorage
        while (true) {
            OutputView.showStorage(storage)
            OutputView.showShoppingGuide()

            val orders = Parser.orderParse(InputView.readLine())
            processOrder(orders = orders, storage = storage)
        }
    }

    fun processOrder(orders: List<Order>, storage: List<Product>) {
        for (order in orders) {
            val targetProducts = storage.filter { product -> product.name == order.productName }
            if (targetProducts.isEmpty()) {
                throw NoSuchElementException("상품명과 일치하는 상품이 없습니다.");
            }

            if (targetProducts.size > 1) {
                promoProcess(targetProducts, order)
            }
        }

    }

    fun promoProcess(targetProducts: List<Product>, order: Order) {
        when (val result: PromotionResult = checkPromo(targetProducts, order)) {
            is PromotionResult.Promotional -> {
                OutputView.showPromotional(result.product.name, result.promotion.get)
                val promotionYn = InputView.readLine()
                if (promotionYn == "Y") {
                    order.count += result.promotion.get
                }
            }

            is PromotionResult.NonPromotional -> {
                OutputView.showNonPromotional(result.product.name, result.nonPromotional)
                val nonPromotionYn = InputView.readLine()
                if (nonPromotionYn == "N") {
                    order.count = 0
                }
            }

            PromotionResult.Success -> {
                return
            }
        }
    }
}