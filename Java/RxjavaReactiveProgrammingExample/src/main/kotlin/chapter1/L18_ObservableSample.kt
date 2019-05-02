package chapter1

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun main() {

    val observable = Observable.create(object : ObservableOnSubscribe<String> {

        override fun subscribe(emitter: ObservableEmitter<String>) {

            val datas = arrayOf("Hello World!", "안녕, Rxjava!")

            datas.forEach {

                if (emitter.isDisposed)
                    return

                emitter.onNext(it)

            }

            emitter.onComplete()

        }

    })


    observable
        .observeOn(Schedulers.computation())
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                // 아무것도 하지 않는다.
            }

            override fun onNext(t: String) {
                val threadName = Thread.currentThread().name
                println("${threadName} : ${t}")
            }

            override fun onComplete() {
                val threadName = Thread.currentThread().name
                println("${threadName} : 완료")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        })

    // 잠시 기다린다
    Thread.sleep(500L)

}