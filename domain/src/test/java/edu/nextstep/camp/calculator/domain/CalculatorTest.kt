package edu.nextstep.camp.calculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CalculatorTest {
    lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun `덧셈이 되어야 한다`() {
        //given
        val requested = "11 +    222"
        val expected = 233.toBigInteger()

        //when
        val actual = calculator.evalute(requested)

        //then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun  `뺄셈이 되어야 한다`() {
        //given
        val requested = "11 -    222"
        val expected = (-211).toBigInteger()

        //when
        val actual = calculator.evalute(requested)

        //then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `나눗셈이 되어야 한다`() {
        //given
        val requested = "222 /    2 / 111"
        val expected = 1.toBigInteger()

        //when
        val actual = calculator.evalute(requested)

        //then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `곱셈이 되어야 한다`() {
        //given
        val requested = "222 *    2 * 3"
        val expected = 1332.toBigInteger()

        //when
        val actual = calculator.evalute(requested)

        //then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `입력값이 null일 경우 IllegalArgumentException throw`() {
        //given
        val requested = null
        val expectedInstance = IllegalArgumentException::class.java

        //when
        val actual = runCatching {
            calculator.evalute(requested)
        }.exceptionOrNull()

        //then
        assertThat(actual).isInstanceOf(expectedInstance)

    }

    @Test
    fun `입력값이 공백 일 경우 IllegalArgumentException throw`() {
        //given
        val requested = " "
        val expectedInstance = IllegalArgumentException::class.java

        //when
        val actual = runCatching {
            calculator.evalute(requested)
        }.exceptionOrNull()

        //then
        assertThat(actual).isInstanceOf(expectedInstance)
        assertThat(actual?.message?.startsWith("잘못된 요청입니다.")).isTrue()
    }

    @Test
    fun `사칙연산 기호가 아닌 경우 IllegalArgumentException throw`() {
        //given
        val requested = "121  % 222"
        val expectedInstance = IllegalArgumentException::class.java

        //when
        val actual = runCatching {
            calculator.evalute(requested)
        }.exceptionOrNull()

        //then
        assertThat(actual).isInstanceOf(expectedInstance)
        assertThat(actual?.message).isEqualTo("잘못된 연산자가 포함되었습니다.")
    }

    @Test
    fun `사칙 연산을 모두 포함하는 경우`() {
        //given
        val requested = "2 + 3 * 4 / 2"
        val expected = 10.toBigInteger()

        //when
        val actual = calculator.evalute(requested)

        //then
        assertThat(actual).isEqualTo(expected)
    }
}