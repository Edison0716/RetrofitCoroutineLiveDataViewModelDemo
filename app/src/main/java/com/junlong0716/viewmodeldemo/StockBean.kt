package com.junlong0716.viewmodeldemo

import java.io.Serializable

/**
 * FileName: StockBean
 * Author:   EdisonLi的Windows
 * Date:     2019/6/6 10:15
 * Description:
 */
data class StockBean(val error_code: Int, val reason: String, val result: List<Result>, val resultcode: String) :
    Serializable

data class Result(
    val `data`: Data,
    val dapandata: Dapandata,
    val gopicture: Gopicture
)

data class Data(
    val buyFive: String,
    val buyFivePri: String,
    val buyFour: String,
    val buyFourPri: String,
    val buyOne: String,
    val buyOnePri: String,
    val buyThree: String,
    val buyThreePri: String,
    val buyTwo: String,
    val buyTwoPri: String,
    val competitivePri: String,
    val date: String,
    val gid: String,
    val increPer: String,
    val increase: String,
    val name: String,
    val nowPri: String,
    val reservePri: String,
    val sellFive: String,
    val sellFivePri: String,
    val sellFour: String,
    val sellFourPri: String,
    val sellOne: String,
    val sellOnePri: String,
    val sellThree: String,
    val sellThreePri: String,
    val sellTwo: String,
    val sellTwoPri: String,
    val time: String,
    val todayMax: String,
    val todayMin: String,
    val todayStartPri: String,
    val traAmount: String,
    val traNumber: String,
    val yestodEndPri: String
)

data class Dapandata(
    val dot: String,
    val name: String,
    val nowPic: String,
    val rate: String,
    val traAmount: String,
    val traNumber: String
)

data class Gopicture(
    val dayurl: String,
    val minurl: String,
    val monthurl: String,
    val weekurl: String
)

