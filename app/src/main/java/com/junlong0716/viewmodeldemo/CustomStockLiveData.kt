package com.junlong0716.viewmodeldemo

import androidx.lifecycle.LiveData

/**
 * FileName: CustomStockLiveData
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 14:56
 * Description:
 */
class CustomStockLiveData : LiveData<StockBean>() {
    companion object {
        //单例
        @Volatile
        private var INSTANCE: CustomStockLiveData? = null

        val instance: CustomStockLiveData
            get() {
                if (INSTANCE == null) {
                    synchronized(CustomStockLiveData::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = CustomStockLiveData()
                        }
                    }
                }
                return INSTANCE!!
            }
    }

    override fun onActive() {
        //活跃的观察者观察时调用
        super.onActive()
    }

    override fun onInactive() {
        //不活跃观察者时调用
        super.onInactive()
    }
}