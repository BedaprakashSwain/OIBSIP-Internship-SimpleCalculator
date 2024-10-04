package com.example.simplecalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplecalculator.R
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentInput = ""
    private var operator: Char? = null
    private var num1: Double? = null
    private val decimalFormat = DecimalFormat("#.########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.textViewResult)

        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val addButton: Button = findViewById(R.id.buttonPlus)
        val subtractButton: Button = findViewById(R.id.buttonMinus)
        val plusMinusButton: Button = findViewById(R.id.buttonPlusMinusSign)
        val percentageButton: Button = findViewById(R.id.buttonPercentage)
        val multiplyButton: Button = findViewById(R.id.buttonMultiply)
        val divideButton: Button = findViewById(R.id.buttonDivide)
        val equalButton: Button = findViewById(R.id.buttonEqual)
        val clearButton: Button = findViewById(R.id.buttonClear)
        val dotButton: Button = findViewById(R.id.buttonDot)

        // Number button listeners
        button0.setOnClickListener { appendToInput("0") }
        button1.setOnClickListener { appendToInput("1") }
        button2.setOnClickListener { appendToInput("2") }
        button3.setOnClickListener { appendToInput("3") }
        button4.setOnClickListener { appendToInput("4") }
        button5.setOnClickListener { appendToInput("5") }
        button6.setOnClickListener { appendToInput("6") }
        button7.setOnClickListener { appendToInput("7") }
        button8.setOnClickListener { appendToInput("8") }
        button9.setOnClickListener { appendToInput("9") }

        // Operator buttons
        addButton.setOnClickListener { onOperatorClick('+') }
        subtractButton.setOnClickListener { onOperatorClick('-') }
        multiplyButton.setOnClickListener { onOperatorClick('*') }
        divideButton.setOnClickListener { onOperatorClick('/') }

        // Equal button
        equalButton.setOnClickListener { calculateResult() }

        // Dot button
        dotButton.setOnClickListener { appendDot() }

        // Clear button
        clearButton.setOnClickListener {
            resultTextView.text = "0"
            currentInput = ""
            operator = null
            num1 = null
        }

        // Plus-minus toggle button
        plusMinusButton.setOnClickListener { togglePlusMinus() }

        // Percentage button
        percentageButton.setOnClickListener { applyPercentage() }
    }

    private fun appendToInput(value: String) {
        currentInput += value
        resultTextView.text = currentInput
    }

    private fun onOperatorClick(op: Char) {
        if (currentInput.isNotEmpty()) {
            num1 = currentInput.toDoubleOrNull()
            operator = op
            currentInput = ""
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        val num2 = currentInput.toDoubleOrNull()

        if (num1 != null && num2 != null && operator != null) {
            val result = when (operator) {
                '+' -> num1!! + num2
                '-' -> num1!! - num2
                '*' -> num1!! * num2
                '/' -> {
                    if (num2 != 0.0) {
                        num1!! / num2
                    } else {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                else -> return
            }

            resultTextView.text = decimalFormat.format(result)
            currentInput = result.toString()
            operator = null
        }
    }

    private fun appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            resultTextView.text = currentInput
        }
    }

    private fun togglePlusMinus() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            resultTextView.text = currentInput
        }
    }

    private fun applyPercentage() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull()
            if (value != null) {
                currentInput = (value / 100).toString()
                resultTextView.text = currentInput
            }
        }
    }
}
