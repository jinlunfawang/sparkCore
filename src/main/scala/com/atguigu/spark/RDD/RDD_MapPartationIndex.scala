package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 获取每个分区数据的最大值和分区号
  */
object RDD_MapPartationIndex {
  def main (args: Array[String] ): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("getLogUrl"))
    /**
      * 三个区
      * 0         [0,1)     1*5/4=1     2
      * 1         [1,2)    2*5/4=2      4
      * 2         [2,3)     3*5/4=3     1
      * 3         [3,5)                5
      */
    val rdd = sparkContext.makeRDD(List(2,4,1,3,5),4)
    // 分区号和 index 表示分区号 iter 表示分区的数据
    val value = rdd.mapPartitionsWithIndex((index, iter) => {

      if(index==1){
      List((index, iter.max)).iterator}else{
        Nil.iterator
      }
    })

    println(value.collect().mkString("\n"))
}
}
