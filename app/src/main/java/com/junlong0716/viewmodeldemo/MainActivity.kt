package com.junlong0716.viewmodeldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mTimes = 0
    private var mStockViewModel:StockViewModel?=null 
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ViewModel 监听
        mStockViewModel = ViewModelProviders.of(this).get(StockViewModel::class.java)
        mStockViewModel!!.mStockLiveData.observe(this, Observer {
            tv_stock_name.text = it.result[0].data.name
        })

        mStockViewModel!!.mStockRequestErrorMsg.observe(this, Observer {
            tv_stock_name.text = it
        })

        mStockViewModel!!.mStockLiveData1.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

        mStockViewModel!!.mTestLiveData.observe(this, Observer {
            Toast.makeText(this,"执行",Toast.LENGTH_SHORT).show()
        })

        mStockViewModel!!.mIntervalLikeRxJava.observe(this, Observer {
            mTimes += 1
            Toast.makeText(this,"轮询$mTimes",Toast.LENGTH_SHORT).show()
        })

        mStockViewModel!!.mIntervalCannotCancelable.observe(this, Observer {
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        })

        mStockViewModel!!.channelTest()

        mStockViewModel!!.produceTest()
    }
    
    fun openTask(v: View){
        mStockViewModel!!.cancelTest()
    }
}
