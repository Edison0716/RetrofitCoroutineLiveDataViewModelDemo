package com.junlong0716.viewmodeldemo

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * FileName: CoroutineLifecycleListener
 * Author:   EdisonLi的Windows
 * Date:     2019/6/12 10:36
 * Description:
 */
internal class CoroutineLifecycleListener<T : Job>(private val deferred: T) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!deferred.isCancelled) {
            deferred.cancel()
        }
    }
}

fun LifecycleOwner.coroutineLifecycle(block: suspend () -> Unit): Job {
    val job = GlobalScope.launch {
        block()
    }
    lifecycle.addObserver(CoroutineLifecycleListener(job))
    return job
}

fun <T> LifecycleOwner.postDelay(delay: Long, block: suspend () -> T): Deferred<T> {
    val deferred = GlobalScope.async(Dispatchers.Main) {
        delay(delay)
        block()
    }
    lifecycle.addObserver(CoroutineLifecycleListener(deferred))
    return deferred
}


fun <T> LifecycleOwner.load(context: CoroutineContext = Dispatchers.Unconfined, loader: suspend () -> T): Deferred<T> {
    val deferred = GlobalScope.async(context, start = CoroutineStart.LAZY) {

        loader()
    }
    lifecycle.addObserver(CoroutineLifecycleListener(deferred))
    return deferred
}


infix fun <T> Deferred<T>.then(block: suspend (T) -> Unit): Job {
    return GlobalScope.launch(Dispatchers.Main) {
        try {
            block(this@then.await())
        } catch (e: Exception) {
            Log.e("Coroutine", e.toString())
            throw  e
        }
    }
}
