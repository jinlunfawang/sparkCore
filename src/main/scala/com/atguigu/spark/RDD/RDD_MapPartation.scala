package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 获取每个分区数据的最大值
  */
object RDD_MapPartation {
  def main (args: Array[String] ): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("getLogUrl"))
    /**
      * 四个区
      * 第一个分区    5/4=1       arr[0]=2
      * 第二个分区    5*2/4       arr[1]=4
      * 第三个分区                arr[2]=1
      * 第四个分区    4*5/4=5     arr[3]=3
      *                          arr[4]=5
      */
    val rdd = sparkContext.makeRDD(List(2,4,1,3,5),4)
    val value = rdd.mapPartitions(iter => {
      // TODO iter表示每个分区的数据 可以调用迭代器的方法 例如最小最大值 过滤等
      println(iter.max)
      iter
    })
    println(value.collect().mkString("\n"))
}
}
