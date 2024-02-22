import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel

class HomeScreenModel : ScreenModel {

    var display = mutableStateOf("0")
    private var result = 0.0
    private var isNewOperation = true
    private var currentOperation: String? = null

    fun onInput(number: String) {
        if (isNewOperation) {
            display.value = number
            isNewOperation = false
        } else {
            if (display.value != "0") {
                display.value += number
            } else {
                display.value = number
            }
        }
    }

    fun onOperation(operation: String) {
        if (!isNewOperation) {
            calculate()
        }
        currentOperation = operation
        isNewOperation = true
    }

    fun calculate() {
        val currentNumber = display.value.toDouble()
        result = when (currentOperation) {
            "+" -> result + currentNumber
            "-" -> result - currentNumber
            "*" -> result * currentNumber
            "/" -> if (currentNumber == 0.0) Double.NaN else result / currentNumber
            else -> currentNumber
        }
        display.value = if (result.isNaN()) "Error" else result.toString()
        isNewOperation = true
    }

    fun clear() {
        display.value = "0"
        result = 0.0
        isNewOperation = true
        currentOperation = null
    }

}