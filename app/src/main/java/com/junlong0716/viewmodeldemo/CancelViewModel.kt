package com.junlong0716.viewmodeldemo

import android.util.Log
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * FileName: CancelViewModel
 * Author:   EdisonLi的Windows
 * Date:     2019/6/11 11:28
 * Description:
 */
class CancelViewModel : ViewModel() {
    val mStockLiveData = MutableLiveData<String>()

    fun createCoroutine() {
        viewModelScope.launch(Dispatchers.Main) {
            intervalTest()
        }
    }

    //withContext 切换线程 挂起
    private suspend fun intervalTest() = withContext(Dispatchers.IO) {

        repeat(3000){
            withContext(Dispatchers.IO) {
                delay(2000L)
                Log.d("创建协程1", "2000L")
            }

            withContext(Dispatchers.IO) {
                delay(2000L)
                Log.d("创建协程2", "2000L")
            }

            withContext(Dispatchers.IO) {
                delay(2000L)
                Log.d("创建协程3", "2000L")
            }

            withContext(Dispatchers.IO){
                delay(2000L)
                val requestStockAsync = BaseRetrofitClient.instance.getRetrofitClient().create(Api::class.java)
                    .requestStockAsync("sh601009", "f065fbab3b7e671f6e3cf9b1f8214ee2")
                try {
                    //子线程发送数据
                    mStockLiveData.postValue(requestStockAsync.await().result[0].data.name)
                } catch (e: HttpException) {
                    Log.d("RESULT",e.message)
                } catch (e: Exception) {
                    Log.d("RESULT",e.message)
                }
            }
        }
    }
}