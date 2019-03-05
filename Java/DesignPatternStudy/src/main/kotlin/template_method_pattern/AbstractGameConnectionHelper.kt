package template_method_pattern

abstract class AbstractGameConnectionHelper {

    protected abstract fun doSecurity(str: String): String

    protected abstract fun authentication(id: String, password: String): Boolean

    protected abstract fun authorization(userName: String): Int

    protected abstract fun connection(info: String): String

    fun requestConnection(encodedCode: String): String {

        val decodedInfo = doSecurity(encodedCode)

        if (!authentication("id", "pw"))
            throw IllegalStateException()

        when (authorization("userName")) {
            0 -> "매니저"
            1 -> "유료회원"
            2 -> "무료회원"
            3 -> "권한없음"
            else -> "기타상황"
        }

        return connection(decodedInfo)
    }

}