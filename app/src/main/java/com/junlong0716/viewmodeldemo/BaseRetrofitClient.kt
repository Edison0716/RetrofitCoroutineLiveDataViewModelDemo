package com.junlong0716.viewmodeldemo

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * FileName: BaseRetrofitClinet
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 10:31
 * Description:
 */
class BaseRetrofitClient {
    companion object {
        //单例
        @Volatile
        private var INSTANCE: BaseRetrofitClient? = null

        val instance: BaseRetrofitClient
            get() {
                if (INSTANCE == null) {
                    synchronized(BaseRetrofitClient::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = BaseRetrofitClient()
                        }
                    }
                }
                return INSTANCE!!
            }
    }

    private var mRetrofit: Retrofit? = null

    init {
        val mOkHttpClient = OkHttpClient
            .Builder()
            .cache(Cache(File(App.instance.externalCacheDir, "net_request_cache"), (10 * 1024 * 1024).toLong()))
            .connectTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build()

        mRetrofit = Retrofit.Builder()
            .client(mOkHttpClient)
            .baseUrl("http://web.juhe.cn:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun getRetrofitClient(): Retrofit {
        return mRetrofit!!
    }
}