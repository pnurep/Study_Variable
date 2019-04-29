package chapter1

import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

fun main() {

    Flowable.interval(200L, TimeUnit.MILLISECONDS)
        .subscribe(object : Subscriber<Long> {

            lateinit var subscription: Subscription
            var startTime: Long = 0

            override fun onSubscribe(s: Subscription?) {
                this.subscription = s!!
                this.startTime = System.currentTimeMillis()
                this.subscription.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Long?) {
                // 구독 시작부터 500 밀리초가 지나면 구독을 해지하고 처리를 중지한다.
                if ((System.currentTimeMillis() - startTime) > 500) {
                    this.subscription.cancel() // 구독을 해지한다.
                    println("구독해지")
                    return
                }

                println("data=${t}")
            }

            override fun onComplete() {

            }

            override fun onError(t: Throwable?) {

            }
        })

    // 잠시 기다린다.
    Thread.sleep(2000L)

    // Subscriber 의 처리 흐름
    // 1. onSubscribe 메서드에서 Subscription 을 전달받고 요청을 호출해 통지를 시작한다.
    // 2. onNext 메서드로 200 밀리초 후에 데이터 0 을 받는다.
    // 3. onNext 메서드에서 구독을 시작하고 500 밀리초가 넘었는지 확인한다.
    // 4. 500 밀리초가 지나지 않았으므로 다시 onNext 메서드에서 데이터를 출력한다.
    // 5. 400 밀리초 후에 데이터 1 을 받는다.
    // 6. 구독을 시작한 뒤 500 밀리초가 넘었는지 확인한다.
    // 7. 500 밀리초가 지나지 않았으므로 데이터를 출력한다.
    // 8. onNext 메서드에서 600 밀리초 후에 다시 데이터 2 를 받는다.
    // 9. 구독을 시작한 뒤 500 밀리초가 넘었는지 확인한다.
    // 10. 시간이 지났으므로 Subscription 의 cancel 메서드를 호출한 뒤 구독해지 를 출력하고 onNext 메서드에서 빠져나온다.
    // 11. 구독이 해지됐으므로 더 이상 통지하지 않는다.


}