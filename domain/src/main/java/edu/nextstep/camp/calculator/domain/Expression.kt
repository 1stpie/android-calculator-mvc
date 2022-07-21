package edu.nextstep.camp.calculator.domain

import kotlin.math.max

sealed class Expression {
    data class Operating(
        private val left: Operand,
        private val operator: Operator
    ) : Expression() {
        operator fun plus(right: Operand) = operator(left, right)
    }

    sealed class Operator(private val operatorChar: Char) : Expression() {
        object Plus : Operator('+')
        object Minus : Operator('-')
        object Div : Operator('/')
        object Mult : Operator('*')

        operator fun invoke(left: Operand, right: Operand): Operand = when (this) {
            is Plus -> left + right
            is Minus -> left - right
            is Div -> left / right
            is Mult -> left * right
        }

        override fun toString(): String {
            return "$operatorChar"
        }

        companion object {
            fun find(char: Char): Operator = when (char) {
                '+' -> Plus
                '-' -> Minus
                '/' -> Div
                '*' -> Mult
                else -> throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.")
            }

            fun getChars() = charArrayOf('+', '-', '/', '*')
        }
    }

    data class Operand(
        val number: Double
    ) : Expression() {
        operator fun plus(another: Operand): Operand = Operand(number + another.number)
        operator fun minus(another: Operand): Operand = Operand(number - another.number)
        operator fun div(another: Operand): Operand = Operand(number / another.number)
        operator fun times(another: Operand): Operand = Operand(number * another.number)

        operator fun plus(operator: Operator): Operating = Operating(this, operator)

        override fun toString(): String {
            return "$number"
        }
    }

    companion object {
        val OPERATOR_SPLIT_REGEX = "[0-9\\.]".toRegex()

        fun merge(
            numbers: List<Expression>,
            operators: List<Expression>,
        ): List<Expression> {
            val expressions = arrayListOf<Expression>()
            (0..max(numbers.size, operators.size)).forEachIndexed { index, _ ->
                numbers.getOrNull(index)?.run(expressions::add)
                operators.getOrNull(index)?.run(expressions::add)
            }
            return expressions
        }

        @Throws(IllegalArgumentException::class)
        fun getOperators(text: String): List<Operator> {
            return text
                .split(OPERATOR_SPLIT_REGEX)
                .filter { it.isNotBlank() }
                .map { it.trim().also { trimmed -> if (trimmed.length > 1) throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.") }.last() }
                .map { Operator.find(it) }
        }

        @Throws(IllegalArgumentException::class)
        fun getNumbers(text: String): List<Operand> {
            return text
                .replace(" ", "")
                .split(*Operator.getChars())
                .filter { it.isNotBlank() }
                .map { it.trim().toDoubleOrNull() ?: throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.") }
                .map { Operand((it)) }
        }
    }
}