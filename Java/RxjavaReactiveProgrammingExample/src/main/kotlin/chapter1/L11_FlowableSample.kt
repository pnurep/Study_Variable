package chapter1

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main() {

    //Flowable 생성.
    val flowable = Flowable.create(
        FlowableOnSubscribe<String> {emitter ->

            val datas = arrayOf("Hello, World!", "안녕, Rxjava!")

            datas.forEach {
                // 구독이 해지되면 처리를 중단한다.
                if (emitter.isCancelled)
                    return@forEach

                //데이터를 통지한다.
                emitter.onNext(it)
            }

            //완료를 통지한다.
            emitter.onComplete()

        }
        , BackpressureStrategy.BUFFER)  //초과한 데이터는 버퍼링한다.


    flowable
        .observeOn(Schedulers.computation())        //Subscriber 처리를 개별 스레드에서 실행한다.
        .subscribe(object : Subscriber<String> {    //구독한다.

            var subscription: Subscription? = null  //데이터 개수 요청과 구독 해지를 하는 객체

            //구독시 시작 처리
            override fun onSubscribe(s: Subscription?) {
                this.subscription = s              //Subscription 을 Subscriber 에 보관한다.
                this.subscription?.request(1L)  //받을 데이터 개수를 요청한다.
            }

            //데이터 받을 때 처리
            override fun onNext(t: String?) {
                val threadName = Thread.currentThread().name    //실행중인 스레드 이름을 얻는다.
                println("${threadName} : ${t}")                        //받은 데이터를 출력한다.
                this.subscription?.request(1L)                      //다음에 받을 데이터 개수를 요청한다.
            }

            //완료 통지 시 처리
            override fun onComplete() {
                val threadName = Thread.currentThread().name
                println("${threadName} : 완료")
            }

            //에러 통지 시 처리
            override fun onError(t: Throwable?) {
                t?.printStackTrace()
            }
        })

    //잠시 기다린다.
    Thread.sleep(500L)

    flowable
        .subscribe { println(it) }
    
}