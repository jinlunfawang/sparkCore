package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 根据单词的首写字母进行分组
  *
  */

object RDD_GroupBy {
  def main (args: Array[String] ): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_GroupBy"))
      val rdd1 = sparkContext.makeRDD(List("Hello", "hive", "hbase", "Hadoop"),3)

    val rdd2 = rdd1.groupBy(x => {
      // 或者x(0)
      val str = x.toString.substring(0, 1)

      if ("H".equals(str)) {
        0
      } else if ("h".equals(str)) {
        1
      }

    })
    println(rdd2.collect().mkString(","))
}
}
