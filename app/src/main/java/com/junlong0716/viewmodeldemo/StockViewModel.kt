package com.junlong0716.viewmodeldemo

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import retrofit2.HttpException

/**
 * FileName: StockViewModel
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 10:27
 * Description:
 */
class StockViewModel : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val mStockLiveData = MutableLiveData<StockBean>()
    var mStockLiveData1: LiveData<String> = Transformations.map(mStockLiveData) { it.reason }
    val mStockRequestErrorMsg = MutableLiveData<String>()
    val mTestLiveData = MutableLiveData<Any>()
    val mIntervalLikeRxJava = MutableLiveData<Any>()
    val mIntervalCannotCancelable = MutableLiveData<Any>()

    init {
        viewModelScope.launch {
            requestStockLiveInfo()
            test1()
        }
    }

    private suspend fun requestStockLiveInfo() {
        val job = coroutineScope {
            launch(Dispatchers.IO){
                delay(1000)
                val requestStockAsync = BaseRetrofitClient.instance.getRetrofitClient().create(Api::class.java)
                    .requestStockAsync("sh601009", "f065fbab3b7e671f6e3cf9b1f8214ee2")
                Log.d("------请求一次-----", "------请求一次-----")
                try {
                    //子线程发送数据
                    //todo 这里注意一定要与Activity生命周期进行绑定 否则会报 Cannot invoke setValue on a background thread 异常
                    if (isActive){
                        mStockLiveData.postValue(requestStockAsync.await())
                    }
                } catch (e: HttpException) {
                    mStockRequestErrorMsg.value = e.message()
                } catch (e: Exception) {
                    mStockRequestErrorMsg.value = e.message
                }
            }
        }

        //堵塞线程 等待子协程结束之后释放
        job.join()

        val job1 = coroutineScope {
            launch(Dispatchers.Main) {
                delay(5000)
                mTestLiveData.value = "OK"
            }
        }

        job1.join()

        coroutineScope {
            launch(Dispatchers.Main) {
                try {
                    repeat(Int.MAX_VALUE) {
                        mIntervalLikeRxJava.value = "SEND_EVENT"
                        delay(3000)
                    }
                } finally {
                    Log.e("INTERVAL_LIKE_RXJAVA", "轮询已结束")
                    //不允许取消的协程
                    withContext(NonCancellable) {
                        mIntervalCannotCancelable.value = "已经开启一个不可取消的协程"
                    }

                    //超时即可取消的协程
                    withTimeout(10000L) {

                    }
                }
            }
        }

        delay(5000)
    }


    private fun test1() = runBlocking {
        launch {
            delay(200L)
            Log.d("COROUTINE_SCOPE", "杰")
        }
        coroutineScope {
            launch {
                delay(300L)
                Log.d("COROUTINE_SCOPE", "伦")
            }
            delay(100L)
            Log.d("COROUTINE_SCOPE", "周")
        }
        Log.d("COROUTINE_SCOPE", "over")
    }

    fun channelTest() = runBlocking(Dispatchers.IO) {
        val channel = Channel<Int>()

        launch {
            for (i in 1..5) {
                channel.send(i * i)
            }
            channel.close()
        }

        repeat(5) {
            Log.e("CHANNEL_TEST", channel.receive().toString())
        }
    }


    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce(Dispatchers.IO) {
        for (x in 1..5) send(x * x)
    }

    @ObsoleteCoroutinesApi
    fun produceTest() = runBlocking(Dispatchers.IO) {
        produceSquares().consumeEach {
            Log.e("PRODUCE_TEST", it.toString())
        }
    }


    fun cancelTest() = async{
        val request = launch(Dispatchers.IO) {
            //不会随着父协程的取消而取消
            GlobalScope.launch(Dispatchers.IO) {
                Log.d("我是父协程内部的一个新协程", "我是父协程内部的一个新协程 --- 开启")
                delay(2000)
                Log.d("我是父协程内部的一个新协程", "我是父协程内部的一个新协程 --- 结束")
            }

            //随着父协程的取消而取消
            launch(Dispatchers.IO) {
                delay(100)
                Log.d("我是承袭父协程上下文的子协程", "我是承袭父协程上下文的子协程 --- 开启")
                delay(2000)
                Log.d("我是承袭父协程上下文的子协程", "我是承袭父协程上下文的子协程 --- 关闭")
            }
        }

        delay(500)

        request.cancel()

        delay(1000)
    }
}