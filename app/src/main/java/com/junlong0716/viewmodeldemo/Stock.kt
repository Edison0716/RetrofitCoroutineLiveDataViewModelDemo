package com.junlong0716.viewmodeldemo

/**
 * FileName: Stock
 * Author:   EdisonLi的Windows
 * Date:     2019/6/11 15:26
 * Description:
 */

class Stock(var name:String){
    var age:Int = 0
        get() = field + 1
        set(value) {
            field = if (value < 18){
                -1
            }else{
                value + 1
            }
        }
}