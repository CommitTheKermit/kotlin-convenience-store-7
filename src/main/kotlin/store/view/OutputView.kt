package store.view

import store.constants.Messages
import store.constants.OutputFormat
import store.model.Product

object OutputView {
    fun showWelcome() {
        println(Messages.WELCOME_MESSAGE)
    }

    fun showStorage(products: List<Product>) {
        products.forEach { println(it) }
    }

    fun showShoppingGuide() {
        println(Messages.SHOPPING_GUIDE)
    }

    fun showPromotional(productName: String, count: Int) {
        println(OutputFormat.PROMOTIONAL_NOTIFY.format(productName, count))
    }
    fun showNonPromotional(productName: String, count: Int) {
        println(OutputFormat.NONPROMOTIONAL_NOTIFY.format(productName, count))
    }


}