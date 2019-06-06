package com.junlong0716.viewmodeldemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * FileName: StockViewModel
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 10:27
 * Description:
 */
class StockViewModel : ViewModel() {
    val mStockLiveData = MutableLiveData<StockBean>()
    var mStockLiveData1: LiveData<String> = Transformations.map(mStockLiveData) { it.result[0].data.name }
    val mStockRequestErrorMsg = MutableLiveData<String>()
    val mTestLiveData = MutableLiveData<Any>()
    val mIntervalLikeRxJava = MutableLiveData<Any>()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            requestStockLiveInfo()
        }
    }

    private suspend fun requestStockLiveInfo() {
        val job = GlobalScope.launch(Dispatchers.IO) {
            delay(5000)
            val requestStockAsync = BaseRetrofitClient.instance.getRetrofitClient().create(Api::class.java)
                .requestStockAsync("sh601009", "f065fbab3b7e671f6e3cf9b1f8214ee2")
            Log.d("------请求一次-----", "------请求一次-----")
            try {
                //子线程发送数据
                mStockLiveData.postValue(requestStockAsync.await())
            } catch (e: HttpException) {
                mStockRequestErrorMsg.value = e.message()
            } catch (e: Exception) {
                mStockRequestErrorMsg.value = e.message
            }
        }

        //堵塞线程
        job.join()

        val job1 = GlobalScope.launch(Dispatchers.Main) {
            mTestLiveData.value = "OK"
        }

        //todo 为什么没有挂起？
        delay(5000)

        job1.join()

        GlobalScope.launch(Dispatchers.IO) {
            repeat(Int.MAX_VALUE) {
                mIntervalLikeRxJava.postValue("SEND_EVENT")
                delay(3000)
            }
        }
    }
}