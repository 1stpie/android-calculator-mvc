package edu.nextstep.camp.calculator.domain

data class Expressions(private var content: List<Expression> = emptyList()) {

    fun calculate(): Double = content.reduce { first, second ->
        when {
            first is Expression.Operating && second is Expression.Operand -> first + second
            first is Expression.Operand && second is Expression.Operator -> first + second
            first is Expression.Operator && second is Expression.Operand -> second + first
            first is Expression.Operand && second is Expression.Operating -> second + first
            else -> throw IllegalArgumentException("잘못된 연산자가 포함되었습니다.")
        }
    }.let {
        (it as? Expression.Operand)?.number ?: throw IllegalArgumentException("완성되지 않은 수식입니다.")
    }
}