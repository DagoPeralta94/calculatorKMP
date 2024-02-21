import cafe.adriel.voyager.core.model.ScreenModel

class HomeScreenModel : ScreenModel {

    private var numero1 = 0
    private var numero2 = 1
    private var operador = ""
    private var count = 0

    fun setNumbers() {
        numero1 = 0
        numero2 = 1
        count = 0
    }

    fun suma(numero3: Int): Int {
        numero1 += numero3
        operador = "suma"
        return numero1
    }

    fun resta(numero3: Int): Int {
        if(count == 0) {
            numero1 = numero3
        } else {
            numero1 -= numero3
        }
        count += 1
        operador = "resta"
        return numero1
    }

    fun multiplicar(numero3: Int): Int {
        numero2 *= numero3
        numero1 = numero2
        operador = "multiplicar"
        return numero1
    }

    fun dividir(numero3: Int): Int {
        numero2 /= numero3
        numero1 = numero2
        operador = "dividir"
        return numero1
    }

    fun result(numberCache: Int): Int {
        numero1 = when (operador) {
            "suma" -> suma(numberCache)
            "resta" -> resta(numberCache)
            "multiplicar" -> multiplicar(numberCache)
            "dividir" -> dividir(numberCache)
            else -> 0
        }
        return numero1
    }
}