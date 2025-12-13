package store.view

import store.constants.Messages
import store.constants.OutputFormat
import store.model.OrderResult
import store.model.Product

object OutputView {
    fun showWelcome() {
        println(Messages.WELCOME_MESSAGE)
    }

    fun showStorage(products: List<Product>) {
        for (it in products) {
            var output = "- %s %,d원 ".format(it.name, it.price)
            output += if (it.count != 0) {
                "${it.count}개 "
            } else {
                "재고 없음 "
            }

            if (it.promotion != null) {
                output += it.promotion.name
            }
            println(output)

        }
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

    fun showMemberShip() {
        println(Messages.MEMBERSHIP)
    }

    fun showReceipt(
        orderResults: List<OrderResult>,
        totalCount: Int,
        totalPrice: Int,
        nonPromoPrice: Int,
        promoPrice: Int,
        promoDiscount: Int,
        membershipDiscount: Double
    ) {
        println("===========W 편의점=============")
        println("상품명\t\t수량\t금액")
        orderResults.forEach {
            println("%s\t\t%d \t%,d".format(it.product.name, it.totalCount, it.totalCount * it.product.price))
        }
        println("===========증\t정=============")
        orderResults.forEach {
            println("%s		%d".format(it.product.name, it.promoCount))
        }
        println("==============================")
        println("총구매액\t\t%d\t%,d".format(totalCount, promoPrice + nonPromoPrice))
        println("행사할인\t\t\t-%,d".format(promoDiscount))
        println("멤버십할인\t\t\t-%,.0f".format(membershipDiscount))
        println("내실돈\t\t\t %,.0f".format(promoPrice + nonPromoPrice  - membershipDiscount))
    }


}