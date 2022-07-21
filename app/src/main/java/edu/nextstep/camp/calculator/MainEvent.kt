package edu.nextstep.camp.calculator

import edu.nextstep.camp.calculator.domain.Expression

sealed class MainEvent {
    data class AddNumber(val displayedText: String, val num: Int) : MainEvent()
    data class AddOperator(val displayedText: String, val operator: Expression.Operator) : MainEvent()
    data class Evalute(val displayedText: String) : MainEvent()
    data class DeleteLast(val displayedText: String) : MainEvent()
}
