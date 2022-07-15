package edu.nextstep.camp.calculator

sealed class CalculatorButton {
    data class Number(val num: Int) : CalculatorButton()
    data class Operator(val operator: Char) : CalculatorButton()
    data class Evalute(val operation: String) : CalculatorButton()
    object Delete : CalculatorButton()
}
