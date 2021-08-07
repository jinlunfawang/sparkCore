package com.atguigu.spark

import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 累加器
  *
  * @Author: liangfangwei
  * @Date: 2021/8/1 20:54
  * @version 1.0
  */
object ACC {
  def main (args: Array[String]): Unit = {
    var sc = new SparkContext(new SparkConf().setAppName("").setMaster("local[*]"))
    var rdd1 = sc.makeRDD(List(1, 2, 3, 4), 2)
    val sumAccumulator: LongAccumulator = sc.longAccumulator("sum")

    var result = rdd1.foreach(sumAccumulator.add(_))
    println("----------------")
    // 获取累加器的值 10
    println(sumAccumulator.value)


  }

}
