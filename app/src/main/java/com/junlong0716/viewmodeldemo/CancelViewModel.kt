package com.junlong0716.viewmodeldemo

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

/**
 * FileName: CancelViewModel
 * Author:   EdisonLi的Windows
 * Date:     2019/6/11 11:28
 * Description:
 */
class CancelViewModel : ViewModel(),CoroutineScope by CoroutineScope(Dispatchers.Default) {
    fun createCoroutine() {
        repeat(1000) {
            launch {
                delay((it + 1) * 200L)
                Log.d("创建协程", "延时时间${(it + 1) * 200L}")
            }
        }
    }
}