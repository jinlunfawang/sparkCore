package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2020/8/11 16:36
  * @version 1.0
  */
object RDD_Operator {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("operator"))
    val rdd = sparkContext.makeRDD(List(1,2,3,4),2)
    val rdd1 = rdd.map(x=>{
     println(x+"执行了函数A")
    x})
    // 执行顺序
    val rdd2 = rdd1.map(x=>{
      println(x+"执行了函数B")
      x})

  println(rdd2.collect().mkString(","))
    sparkContext.stop()

  }
}
