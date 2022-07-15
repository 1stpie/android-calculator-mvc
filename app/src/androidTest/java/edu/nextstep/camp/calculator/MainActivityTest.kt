package edu.nextstep.camp.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class MainActivityTest(
    buttonDescription: String,
    private val buttonId: Int,
    private val expectedText: String
) {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        @JvmStatic
        @Parameters(name = "버튼 : {0}, 동작 예상 텍스트 : {2}")
        fun getTestParameters() = listOf(
            arrayOf("버튼0", R.id.button0, "0"),
            arrayOf("버튼1", R.id.button1, "1"),
            arrayOf("버튼2", R.id.button2, "2"),
            arrayOf("버튼3", R.id.button3, "3"),
            arrayOf("버튼4", R.id.button4, "4"),
            arrayOf("버튼5", R.id.button5, "5"),
            arrayOf("버튼6", R.id.button6, "6"),
            arrayOf("버튼7", R.id.button7, "7"),
            arrayOf("버튼8", R.id.button8, "8"),
            arrayOf("버튼9", R.id.button9, "9")
        )
    }

    @Test
    fun `버튼_동작`() {
        //when: 버튼을 누르면
        onView(withId(buttonId)).perform(ViewActions.click())
        //then: 화면에 버튼의 텍스트가 보여야 한다
        onView(withId(R.id.textView)).check(matches(withText(expectedText)))
    }

    //기능 요구 사항
    /*입력된 피연산자가 없을 때, 사용자가 피연산자 0 ~ 9 버튼을 누르면 화면에 해당 숫자가 화면에 보여야 한다.
    -> 1 클릭 -> 1
    5 + -> 1 클릭 -> 5 + 1*/
    @Test
    fun `입력된_피연산자가_없을_때_사용자가_피연산자_0_-_9_버튼을_누르면_화면에_해당_숫자가_화면에_보여야_한다`() {
        onView(withId(R.id.button1)).perform(ViewActions.click())
        onView(withId(R.id.textView)).check(matches(withText("1")))
    }
    /*입력된 피연산자가 있을 때, 기존 숫자 뒤에 해당 숫자가 화면에 보여야 한다. 예를 들면, 8이 입력되어 있을 때 9를 입력하면 89가 보여야 한다.
    8 -> 9 클릭 -> 89*/
    /*입력된 피연산자가 없을 때, 사용자가 연산자 +, -, ×, ÷ 버튼을 누르면 화면에 아무런 변화가 없어야 한다.
    -> + 클릭 ->*/
    /*입력된 피연산자가 있을 때, 사용자가 연산자 +, -, ×, ÷ 버튼을 누르면 해당 기호가 화면에 보여야 한다.
    1 -> + 클릭 -> 1 +
    1 + -> - 클릭 -> 1 -*/
    /*입력된 수식이 없을 때, 사용자가 지우기 버튼을 누르면 화면에 아무런 변화가 없어야 한다.
    -> 지우기 클릭 ->*/
    /*입력된 수식이 있을 때, 사용자가 지우기 버튼을 누르면 수식에 마지막으로 입력된 연산자 또는 피연산자가 지워져야 한다.
    32 + 1 -> 지우기 클릭 -> 32 + -> 지우기 클릭 -> 32 -> 지우기 클릭 -> 3 -> 지우기 클릭 ->  -> 지우기 클릭 ->*/
    /*입력된 수신이 완전할 때, 사용자가 = 버튼을 누르면 입력된 수식의 결과가 화면에 보여야 한다.
    3 + 2 -> = 클릭 -> 5*/
    /*입력된 수식이 완전하지 않을 때, 사용자가 = 버튼을 눌렀을 때 완성되지 않은 수식입니다 토스트 메세지가 화면에 보여야 한다.
    3 + -> = 클릭 -> 완성되지 않은 수식입니다*/
}