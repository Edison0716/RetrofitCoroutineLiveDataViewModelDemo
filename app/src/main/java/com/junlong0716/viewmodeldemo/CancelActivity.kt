package com.junlong0716.viewmodeldemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class CancelActivity : AppCompatActivity() {
    private var mCancelViewModel: CancelViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel)
        mCancelViewModel = ViewModelProviders.of(this).get(CancelViewModel::class.java)
        mCancelViewModel!!.createCoroutine()
        val stock = Stock("haha")
        stock.print()

        mCancelViewModel!!.mStockLiveData.observe(this, Observer{
            Toast.makeText(this@CancelActivity,it,Toast.LENGTH_SHORT).show()
        })
    }

    fun Stock.print() {
        print("$name")
    }
}
