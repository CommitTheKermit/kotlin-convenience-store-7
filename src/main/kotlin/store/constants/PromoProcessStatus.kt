package store.constants

enum class PromoProcessStatus(val status: String) {
    PROMO_NORMAL("프로모션 정상"),
    APPLICABLE("프로모션 적용 가능"),
    INSUFFICIENT("프로모션 상품 부족"),
    NORMAL("일반 상품 정상")
}