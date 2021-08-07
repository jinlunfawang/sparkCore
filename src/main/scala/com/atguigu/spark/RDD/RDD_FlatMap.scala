package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}


/**
 * 将List(List(1,2),3,List(4,5))进行扁平化操作
 */

object RDD_FlatMap {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
    val value1 = sparkContext.makeRDD(List(List(1, 2, 3, 4), 5, "hello"), 1)
    val value2 = value1.flatMap(data => {
      data match {
        case list: List[_] => list
        case s: String => List(s)
        case d: Int => List(d)
      }

    })
    // 1:2:3:4:5:hello
    println(value2.collect().mkString(":"))
    println("--------")

  }
}
