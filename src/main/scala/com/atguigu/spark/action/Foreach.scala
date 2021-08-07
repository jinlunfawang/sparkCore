package com.atguigu.spark.action

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @Author: liangfangwei
  * @Date: 2021/7/31 15:42
  * @version 1.0
  */
object Foreach {
  def main (args: Array[String]): Unit = {
    val sc=new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""))
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2,3,4), 2)


    /**
      * 输出结果 1 2 3 4
      */
    rdd.collect().foreach(println)
    println("***************")
    /**
      * 3 1 4 2
      */
    rdd.foreach(println)
  }
}
