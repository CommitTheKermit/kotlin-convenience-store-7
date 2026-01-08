package store.view

import store.model.Product

object OutputView {
    fun showStartGuide() {
        println(
            """
            안녕하세요. W편의점입니다.
            현재 보유하고 있는 상품입니다.
            
            """.trimIndent()
        )
    }

    fun showProduct(products: List<Product>) {
        for (it in products) {
            if (it.quantity == 0) {
                println("- %s %,d원 재고없음 %s".format(it.name, it.price, it.promotion?.name ?: ""))
                continue
            }
            println("- %s %,d %d개 %s".format(it.name, it.price, it.quantity, it.promotion?.name ?: ""))
        }
    }

    fun showProductInputGuide() {
        println(
            "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"
        )
    }

    fun showError(error: String) {
        println(error)
    }

}


//안녕하세요. W편의점입니다.
//현재 보유하고 있는 상품입니다.
//
//- 콜라 1,000원 10개 탄산2+1
//- 콜라 1,000원 10개
//- 사이다 1,000원 8개 탄산2+1
//- 사이다 1,000원 7개
//- 오렌지주스 1,800원 9개 MD추천상품
//- 오렌지주스 1,800원 재고 없음
//- 탄산수 1,200원 5개 탄산2+1
//- 탄산수 1,200원 재고 없음
//- 물 500원 10개
//- 비타민워터 1,500원 6개
//- 감자칩 1,500원 5개 반짝할인
//- 감자칩 1,500원 5개
//- 초코바 1,200원 5개 MD추천상품
//- 초코바 1,200원 5개
//- 에너지바 2,000원 5개
//- 정식도시락 6,400원 8개
//- 컵라면 1,700원 1개 MD추천상품
//- 컵라면 1,700원 10개
//
//구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
