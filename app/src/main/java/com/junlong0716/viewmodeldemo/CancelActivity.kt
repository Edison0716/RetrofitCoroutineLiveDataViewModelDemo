package com.junlong0716.viewmodeldemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.cancel

class CancelActivity : AppCompatActivity(){

    private var mCancelViewModel:CancelViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel)
        mCancelViewModel = ViewModelProviders.of(this).get(CancelViewModel::class.java)
        mCancelViewModel!!.createCoroutine()
        val stock = Stock("haha")
        stock.print()
    }

    fun Stock.print(){
        print("$name")
    }

    override fun onDestroy() {
        super.onDestroy()
        mCancelViewModel!!.cancel()
    }
}
