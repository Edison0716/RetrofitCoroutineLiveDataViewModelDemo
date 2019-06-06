package com.junlong0716.viewmodeldemo

import android.app.Application
import kotlin.properties.Delegates

/**
 * FileName: App
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 10:47
 * Description:
 */
class App:Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
       var instance:App by Delegates.notNull()
    }
}
