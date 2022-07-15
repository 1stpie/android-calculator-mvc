package edu.nextstep.camp.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.nextstep.camp.calculator.databinding.ActivityMainBinding
import edu.nextstep.camp.calculator.domain.Calculator
import edu.nextstep.camp.calculator.domain.Operation

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        initLayout()
    }

    private fun initLayout() {
        setContentView(binding.root)
        binding.button0.setOnClickListener { onButtonClick(CalculatorButton.Number(0)) }
        binding.button1.setOnClickListener { onButtonClick(CalculatorButton.Number(1)) }
        binding.button2.setOnClickListener { onButtonClick(CalculatorButton.Number(2)) }
        binding.button3.setOnClickListener { onButtonClick(CalculatorButton.Number(3)) }
        binding.button4.setOnClickListener { onButtonClick(CalculatorButton.Number(4)) }
        binding.button5.setOnClickListener { onButtonClick(CalculatorButton.Number(5)) }
        binding.button6.setOnClickListener { onButtonClick(CalculatorButton.Number(6)) }
        binding.button7.setOnClickListener { onButtonClick(CalculatorButton.Number(7)) }
        binding.button8.setOnClickListener { onButtonClick(CalculatorButton.Number(8)) }
        binding.button9.setOnClickListener { onButtonClick(CalculatorButton.Number(9)) }
        binding.buttonDelete.setOnClickListener { onButtonClick(CalculatorButton.Delete) }
        binding.buttonDivide.setOnClickListener { onButtonClick(CalculatorButton.Operator(Operation.Div.operator)) }
        binding.buttonPlus.setOnClickListener { onButtonClick(CalculatorButton.Operator(Operation.Plus.operator)) }
        binding.buttonMinus.setOnClickListener { onButtonClick(CalculatorButton.Operator(Operation.Minus.operator)) }
        binding.buttonMultiply.setOnClickListener { onButtonClick(CalculatorButton.Operator(Operation.Mult.operator)) }
        binding.buttonEquals.setOnClickListener { onButtonClick(CalculatorButton.Evalute("${binding.textView.text}")) }
    }

    private fun onButtonClick(event: CalculatorButton) {
        when(event) {
            is CalculatorButton.Delete -> eventDeleteLast()
            is CalculatorButton.Operator -> eventAddOperator(event)
            is CalculatorButton.Number -> eventAddNumber(event)
            is CalculatorButton.Evalute -> eventEvalute(event)
        }
    }

    private fun eventDeleteLast() {
        val text = binding.textView.text
            .dropLastWhile { it == ' ' }
            .dropLast(1)
            .dropLastWhile { it == ' ' }

        binding.textView.text = text
    }

    private fun eventAddOperator(event: CalculatorButton.Operator) {
        if (getTextViewLastChar()?.isDigit() != true) return

        val text = "${binding.textView.text} ${event.operator}"
        binding.textView.text = text
    }

    private fun eventEvalute(event: CalculatorButton.Evalute) {
        runCatching {
            Calculator().evalute(event.operation)
        }.onSuccess {
            binding.textView.text = "$it"
        }.onFailure {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun eventAddNumber(event: CalculatorButton.Number) {
        val appendText = getAppendNumberText(event)
        val text = "${binding.textView.text}${appendText}"
        binding.textView.text = text
    }

    private fun getAppendNumberText(event: CalculatorButton.Number, lastChar: Char? = getTextViewLastChar()) = when {
        lastChar == null -> "${event.num}"
        lastChar.isDigit() -> "${event.num}"
        lastChar == ' ' -> "${event.num}"
        else -> " ${event.num}"
    }

    private fun getTextViewLastChar() = binding.textView.text.getOrNull(binding.textView.text.lastIndex)
}
