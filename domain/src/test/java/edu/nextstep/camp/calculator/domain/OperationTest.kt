package edu.nextstep.camp.calculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.math.BigInteger

@RunWith(Parameterized::class)
class OperationTest(
    operator: Char,
    private val operation: Operation,
    private val left: BigInteger,
    private val right: BigInteger,
    private val expected: BigInteger
) {

    companion object {
        @JvmStatic
        @Parameters(name = " {2} {0} {3} = {4} ")
        fun getTestParameters() = listOf(
            arrayOf(Operation.Plus.operator, Operation.Plus, 2.toBigInteger(), 3.toBigInteger(), 5.toBigInteger()),
            arrayOf(Operation.Minus.operator, Operation.Minus, 2.toBigInteger(), 3.toBigInteger(), (-1).toBigInteger()),
            arrayOf(Operation.Div.operator,  Operation.Div, 6.toBigInteger(), 3.toBigInteger(), 2.toBigInteger()),
            arrayOf(Operation.Mult.operator, Operation.Mult, 2.toBigInteger(), 3.toBigInteger(), 6.toBigInteger()),
        )
    }

    @Test
    fun `연산`() {
        //when

        val actual = operation(left, right)

        //then
        assertThat(actual).isEqualTo(expected)
    }
}