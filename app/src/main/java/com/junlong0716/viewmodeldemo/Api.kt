package com.junlong0716.viewmodeldemo

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * FileName: Api
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 10:35
 * Description:
 */
interface Api{
    @GET("finance/stock/hs")
    fun requestStockAsync(@Query("gid") gid: String, @Query("key") key: String): Deferred<StockBean>
}