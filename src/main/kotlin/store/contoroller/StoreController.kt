package store.contoroller

import store.model.Parser
import store.model.Product
import store.model.Storage
import store.model.Store
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
            Store.processOrder(orders = orders, storage = storage)


        }
    }
}