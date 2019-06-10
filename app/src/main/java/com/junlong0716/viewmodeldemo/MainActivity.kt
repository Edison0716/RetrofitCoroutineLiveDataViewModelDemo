package com.junlong0716.viewmodeldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mTimes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ViewModel 监听
        val stockViewModel = ViewModelProviders.of(this).get(StockViewModel::class.java)
        stockViewModel.mStockLiveData.observe(this, Observer {
            tv_stock_name.text = it.result[0].data.name
        })

        stockViewModel.mStockRequestErrorMsg.observe(this, Observer {
            tv_stock_name.text = it
        })

        stockViewModel.mStockLiveData1.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

        stockViewModel.mTestLiveData.observe(this, Observer {
            Toast.makeText(this,"执行",Toast.LENGTH_SHORT).show()
        })

        stockViewModel.mIntervalLikeRxJava.observe(this, Observer {
            mTimes += 1
            Toast.makeText(this,"轮询$mTimes",Toast.LENGTH_SHORT).show()
        })

        stockViewModel.mIntervalCannotCancelable.observe(this, Observer {
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        })

        stockViewModel.channelTest()

        stockViewModel.produceTest()
    }
}
