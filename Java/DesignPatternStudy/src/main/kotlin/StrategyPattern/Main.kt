package StrategyPattern

fun main() {

    val aInterface = AInterfaceImpl().apply {
        funcA()
    }

    val aObj = AObj(aInterface).apply {
        funcAA()
    }

}