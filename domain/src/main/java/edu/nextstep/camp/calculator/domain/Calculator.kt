package edu.nextstep.camp.calculator.domain

import java.math.BigInteger

class Calculator {
    private companion object {
        val OPERATOR_SPLIT_REGEX = "[0-9]".toRegex()
    }

    @Throws(IllegalArgumentException::class)
    fun evalute(text: String?): BigInteger {
        require(!text.isNullOrBlank()) { "잘못된 요청입니다. : $text" }
        return compute(getNumbers(text), getOperators(text))
    }

    @Throws(IllegalArgumentException::class)
    private fun compute(numbers: List<BigInteger>, operators: List<Char>): BigInteger {
        require(operators.size == numbers.size-1) {"완성되지 않은 수식입니다."}
        return numbers.reduceIndexed { index, left, right ->
            val operator = Operation.get(operators[index - 1])
            operator(left, right)
        }
    }

    private fun getOperators(text: String): List<Char> {
        return text
            .split(OPERATOR_SPLIT_REGEX)
            .filter { it.isNotBlank() }
            .map { it.trim().last() }
    }

    @Throws(IllegalArgumentException::class)
    private fun getNumbers(text: String): List<BigInteger> {
        return text
            .replace(" ", "")
            .split(*Operation.getChars())
            .filter { it.isNotBlank() }
            .map { it.trim().toBigIntegerOrNull() ?: throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.") }
    }
}
