package com.atguigu.spark.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/7/7 10:25
  * @version 1.0
  */
object Reduce {
  def main (args: Array[String]): Unit = {
    val sc=new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""))
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2), 2)
    // 将该RDD所有元素相加得到结果
    //val result: Int = rdd.aggregate(0)(_ + _, _ + _)
    val result: Int = rdd.aggregate(10)(_ + _, _ + _)
    println(result)
  }
}
