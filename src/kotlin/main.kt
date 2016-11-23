import rx.Observable
import rx.functions.Action1
import rx.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    beheavierSubject()
}

private fun addSomeDelay() {
    try {
        System.out.println("Wait for some seconds")
        Thread.sleep(3000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}

private val subscriber1 = Action1<Long> {
    println("Subscriber 1 :" + it)
}

private val subscriber2 = Action1<Long> {
    println("Subscriber 2 :" + it)
}

private fun beheavierSubject() {
    val cold = Observable.interval(1000, TimeUnit.MILLISECONDS)

    val behaviorSubject = BehaviorSubject.create(-1L)
    cold.subscribe(behaviorSubject)

    addSomeDelay()

    behaviorSubject.subscribe(subscriber1)
    behaviorSubject.subscribe(subscriber2)

    addSomeDelay()
}

