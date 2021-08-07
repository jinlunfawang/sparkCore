package com.atguigu.spark.RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * ReduceByKey
  */

object RDD_GroupBykey {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
    val value1 :RDD[(String,Int)]= sparkContext
      .makeRDD(List(("a",1),("a",2),("b",2),("c",1),("a",1),("a",2),("b",2),("c",1)), 2)
   val unit = value1.groupByKey()
    unit.saveAsTextFile("output8")

  }
}
