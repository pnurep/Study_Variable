package StrategyPattern

class AObj(private val aInterface: AInterface) {

    fun funcAA() {
        //Delegate
        aInterface.funcA()
        aInterface.funcA()
    }

}