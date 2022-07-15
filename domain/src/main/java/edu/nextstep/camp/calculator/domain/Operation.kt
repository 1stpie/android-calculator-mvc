package edu.nextstep.camp.calculator.domain

import java.math.BigInteger

sealed class Operation(
    val operator: Char,
    private val calculationBlock: (BigInteger, BigInteger) -> BigInteger
) {
    object Plus : Operation('+', { left, right -> left + right })
    object Minus : Operation('-', { left, right -> left - right })
    object Div : Operation('/', { left, right -> left / right })
    object Mult : Operation('*', { left, right -> left * right })

    operator fun invoke(left: BigInteger, right: BigInteger) = calculationBlock(left, right)

    companion object {
        @Throws(IllegalArgumentException::class)
        fun get(operator: Char): Operation = when (operator) {
            Plus.operator -> Plus
            Minus.operator -> Minus
            Div.operator -> Div
            Mult.operator -> Mult
            else -> throw IllegalArgumentException("wrong operator [$operator]")
        }

        fun getChars() = charArrayOf(Plus.operator, Minus.operator, Div.operator, Mult.operator)
    }
}
