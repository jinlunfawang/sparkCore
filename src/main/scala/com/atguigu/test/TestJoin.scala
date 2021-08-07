package com.atguigu.test

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Liang on 2021/8/4.
 */
object TestJoin {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("1"))

    val list = List(5,1,8,2,-3,4)
    val list3 = List(5,1,8,2,-3,4)

    val list2 = List((1, 5), (2, 1), (3, 8), (6, 2), (4, -3), (9, 4))

   var rdd1= sc.makeRDD(list2)
   var rdd2= sc.makeRDD(list)
    rdd1.leftOuterJoin(rdd1).collect().foreach(println)

  }
}
