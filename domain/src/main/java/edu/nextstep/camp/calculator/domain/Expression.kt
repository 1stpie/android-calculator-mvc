package edu.nextstep.camp.calculator.domain

sealed class Expression {
    object EMPTY : Expression()

    data class Operating(
            private val left: Operand,
            private val operator: Operator
        ): Expression() {

        operator fun invoke(right: Operand) = operator(left, right)
    }

    sealed class Operator : Expression() {
        object Plus : Operator()
        object Minus : Operator()
        object Div : Operator()
        object Mult : Operator()

        operator fun invoke(left: Operand, right: Operand) : Operand = when(this) {
            is Plus -> left + right
            is Minus -> left - right
            is Div -> left / right
            is Mult -> left * right
        }

        companion object {
            fun find(char: Char): Operator = when(char) {
                '+' -> Plus
                '-' -> Minus
                '/' -> Div
                '*' -> Mult
                else -> throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.")
            }
        }
    }

    data class Operand(
        val number: Double
    ) : Expression() {
        operator fun plus(another: Operand): Operand {

            return Operand(number + another.number)
        }

        operator fun plus(operator: Operator): Operating = Operating(this, operator)

        operator fun minus(another: Operand): Operand {
            return Operand(number - another.number)
        }

        operator fun div(another: Operand): Operand {
            return Operand(number / another.number)
        }

        operator fun times(another: Operand): Operand {
            return Operand(number * another.number)
        }
    }

    fun test() {

    }
}