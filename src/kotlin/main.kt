import rx.Observable
import rx.functions.Action1
import rx.subjects.AsyncSubject
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val cold = Observable.create<Long> { subscriber ->
        for (i in 0..1) {
            println("Source Emit " + i)
            subscriber.onNext(i.toLong())
        }
    }

    val coldInterval = Observable.interval(1000, TimeUnit.MILLISECONDS)

    //coldObservable(cold)
    //hotObservable(cold)

    asyncSubject(cold)
    //behaviorSubject(coldInterval)
    //behaviorSubject2(coldInterval)
    //publishSubject(coldInterval)
    //replaySubject(coldInterval)
}

private val subscriber1 = Action1<Long> {
    println("Subscriber 1 :" + it)
}

private val subscriber2 = Action1<Long> {
    println("Subscriber 2 :" + it)
}

private fun coldObservable(cold: Observable<Long>) {
    cold.subscribe(subscriber1)
    cold.subscribe(subscriber2)
}

private fun hotObservable(cold: Observable<Long>) {
    val connectble = cold.publish()
    connectble.subscribe(subscriber1)
    connectble.subscribe(subscriber2)
    connectble.connect()
}

private fun asyncSubject(cold: Observable<Long>) {
    val asyncSubject = AsyncSubject.create<Long>()
    cold.subscribe(asyncSubject)
    asyncSubject.subscribe(subscriber1)
    asyncSubject.subscribe(subscriber2)
}

private fun behaviorSubject(cold: Observable<Long>) {
    val behaviorSubject = BehaviorSubject.create(-1L)
    cold.subscribe(behaviorSubject)

    addSomeDelay()

    behaviorSubject.subscribe(subscriber1)
    behaviorSubject.subscribe(subscriber2)

    addSomeDelay()
}

private fun behaviorSubject2(cold: Observable<Long>) {
    val behaviorSubject = BehaviorSubject.create(-1L)

    behaviorSubject.subscribe(subscriber1)
    behaviorSubject.subscribe(subscriber2)

    cold.subscribe(behaviorSubject)

    addSomeDelay()
}

private fun publishSubject(cold: Observable<Long>) {
    val publishSubject = PublishSubject.create<Long>()
    cold.subscribe(publishSubject)

    addSomeDelay()

    publishSubject.subscribe(subscriber1)
    publishSubject.subscribe(subscriber2)

    addSomeDelay()
}

private fun replaySubject(cold: Observable<Long>) {
    val publishSubject = ReplaySubject.create<Long>()
    cold.subscribe(publishSubject)

    addSomeDelay()

    publishSubject.subscribe(subscriber1)
    publishSubject.subscribe(subscriber2)

    addSomeDelay()
}

/***
 * Helper Function
 */
private fun addSomeDelay() {
    try {
        System.out.println("Wait for some seconds")
        Thread.sleep(6000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}