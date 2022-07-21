package edu.nextstep.camp.calculator.domain

class Calculator {

    @Throws(IllegalArgumentException::class)
    fun evalute(text: String?): Double {
        require(!text.isNullOrBlank()) { "잘못된 요청입니다. : $text" }
        return getExpressions(getArrangedRequest(text)).calculate()
    }

    private fun getArrangedRequest(text: String): String = if (text[0] == '-') "0$text" else text

    private fun getExpressions(text: String): Expressions {
        return Expressions(
            Expression.merge(
                Expression.getNumbers(text),
                Expression.getOperators(text)
            )
        )
    }
}
