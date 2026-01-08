package store.constants

enum class PromoProcessStatus(val status: String) {
    NORMAL("정상"),
    APPLICABLE("프로모션 적용 가능"),
    INSUFFICIENT("프로모션 상품 부족")
}