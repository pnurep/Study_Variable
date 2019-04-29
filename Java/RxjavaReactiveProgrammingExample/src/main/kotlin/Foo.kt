import io.reactivex.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun main() {
//    Session.createSession()
//    Session.weird()
//
//    println(BoxStore.Companion.BoxKeeper.instance)
//
//    Test.access()


    foo()

}

fun foo() {
    val countDownLatch = CountDownLatch(1)
    var a = false

    Observable.interval(0, 1, TimeUnit.SECONDS)
        .map {
            if (it>10) {
                a = true
                it
            } else
                it }
        .filter { a }
        .take(1)
        .subscribe { l: Long ->

            println(l)
            countDownLatch.countDown()

        }

    countDownLatch.await()

}

class Session {

    companion object INSTANCE {

        private lateinit var session: Session

        fun createSession() = Session().apply {
            session = this
        }
        fun weird() {
            //Session.INSTANCE::session.isAbstract // this works
            Session.INSTANCE::session.isInitialized // java.lang.NoSuchFieldError: session ???!!!
        }
    }

}
class BoxStore {
    companion object {

        private lateinit var box: BoxStore
        class BoxKeeper private constructor() {
            companion object {
                var instance: BoxStore
                    get() = box
                    set(_) {}
                init {
                    if (::box.isInitialized.not())
                        box = BoxStore()
                }
            }

        }
    }

}
class Test {
    companion object {
        lateinit var buggy: Collection<Int>
        fun access(): Boolean = this::buggy.isInitialized
    }



}
