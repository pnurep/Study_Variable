package template_method_pattern

class DefaultGameConnectionHelper : AbstractGameConnectionHelper() {

    override fun doSecurity(str: String): String {
        println("디코드")
        return str
    }

    override fun authentication(id: String, password: String): Boolean {
        println("아이디/암호 확인과정")
        return true
    }

    override fun authorization(userName: String): Int {
        println("권한확인")
        return 0
    }

    override fun connection(info: String): String {
        println("마지막 접속단계")
        return info
    }

}