package store.constants

enum class OutputFormat(private val template: String, private val argCount: Int) {

    PROMOTIONAL_NOTIFY("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", 2),
    NONPROMOTIONAL_NOTIFY("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", 2);


    fun format(vararg args: Any): String {
        require(argCount == args.size) { ErrorMessages.NOT_EQUAL_ARGUMENT_COUNT }

        return template.format(*args)
    }

}
