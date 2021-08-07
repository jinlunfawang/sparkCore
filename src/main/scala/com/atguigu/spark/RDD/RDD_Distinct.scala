package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 随机数抽取
  */

object RDD_Distinct {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
    val value1 = sparkContext
      .makeRDD(List(1, 2, 1, 2, 3, 4, 3,4), 2)
      // 去重后改变分区
      .distinct(1)
    println(value1.collect().mkString(","))

    val value2 = sparkContext
      // 去重不改变分区

      .makeRDD(List(1, 2, 1, 2, 3, 4, 3,4), 2).distinct().coalesce(2)
    println(value2.collect().mkString(","))

  }
}


