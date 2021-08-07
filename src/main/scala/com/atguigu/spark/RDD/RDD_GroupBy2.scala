package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 统计不同小时段的apache.log
  *
  */

object RDD_GroupBy2 {
  def main (args: Array[String] ): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_GroupBy"))
      val rdd1 = sparkContext.textFile("input/apache.log")

    val rdd2 = rdd1.groupBy(line => {
    val strings:Array[String] = line.toString.split(" ")
     val stringsTime:Array[String]= strings(3).toString.split(":")
      stringsTime(1).toInt
    })

    rdd2.collect()
    rdd2.saveAsTextFile("output2")

}
}
