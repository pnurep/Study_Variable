package template_method_pattern

fun main() {

    val helper: AbstractGameConnectionHelper = DefaultGameConnectionHelper()

    helper.requestConnection("접속에 필요한 정보")

}