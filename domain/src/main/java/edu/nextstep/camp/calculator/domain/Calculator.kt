package edu.nextstep.camp.calculator.domain

import kotlin.math.max

class Calculator {
    private companion object {
        val OPERATOR_SPLIT_REGEX = "[0-9\\.]".toRegex()
    }

    @Throws(IllegalArgumentException::class)
    fun evalute(text: String?): Double {
        require(!text.isNullOrBlank()) { "잘못된 요청입니다. : $text" }
        val arrangedRequest = getArrangedRequest(text)
        getExpressions(text).content.reduce { acc, right ->
            acc
        }

        return 0.0//compute(getNumbers(arrangedRequest), getOperators(arrangedRequest))
    }

    private fun getArrangedRequest(text: String) = if (text[0] == '-') {
        "0$text"
    } else {
        text
    }

    @Throws(IllegalArgumentException::class)
    private fun compute(numbers: List<Double>, operators: List<Char>): Double {
        require(operators.size == numbers.size-1) {"완성되지 않은 수식입니다."}


        return numbers.reduceIndexed { index, left, right ->
            val operator = Operator.find(operators[index - 1])
            operator.calculate(left, right)
        }
    }

    private fun getExpressions(text: String): Expressions {
        return Expressions(mergeExpressions(getNumbers(text), getOperators(text)))
    }

    private fun mergeExpressions(
        numbers: List<Expression.Operand>,
        operators: List<Expression.Operator>,
    ): ArrayList<*> {
        val expressions = arrayListOf<Expression>()
        (0..max(numbers.size, operators.size)).forEachIndexed { index, _ ->
            numbers.getOrNull(index)?.run(expressions::add)
            operators.getOrNull(index)?.run(expressions::add)
        }
        return expressions
    }

    private fun getOperators(text: String): List<Expression.Operator> {
        return text
            .split(OPERATOR_SPLIT_REGEX)
            .filter { it.isNotBlank() }
            .map { it.trim().also { trimmed -> if(trimmed.length > 1) throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.")}.last() }
            .map { Expression.Operator.find(it) }
    }

    @Throws(IllegalArgumentException::class)
    private fun getNumbers(text: String): List<Expression.Operand>  {
        return text
            .replace(" ", "")
            .split(*Operator.getChars())
            .filter { it.isNotBlank() }
            .map { it.trim().toDoubleOrNull() ?: throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.") }
            .map { Expression.Operand((it)) }
    }
}
