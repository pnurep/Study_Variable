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



fun main() {
    Session.createSession()
    Session.weird()

    println(BoxStore.Companion.BoxKeeper.instance)

    Test.access()
}