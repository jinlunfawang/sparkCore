package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/6/25 22:43
  * @version 1.0
  */
object RDD_OPOperator {
  def main (args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("DobuleOpa").setMaster("local"))

    val rdd1 = sc.makeRDD(List(1, 2, 3, 4, 5), 2)
    val rdd2 = sc.makeRDD(List(4, 5, 6, 7, 8), 2)
    val rdd3 = sc.makeRDD(List("hello", "world"), 2)

    //TODO 并集(1,2,3,4,5,4,5,6,7,8)  总分区数不变,每个分区中的数据也不变
    val rddUnion = rdd1.union(rdd2)
    rddUnion.saveAsTextFile("output111")
    //TODO 交集 (4,5) 保留最大分区数 数据被打乱重组
    val rddIntersection = rdd1.intersection(rdd2)
    rddIntersection.saveAsTextFile("output2")
    //TODO 差集 以调用当前RDD的分区为主
    val rddSubtract = rdd1.subtract(rdd2)
    rddSubtract.saveAsTextFile("output3")
    //TODO 拉链 只能拉链分区数相等 对应分区数据量相等的数据 如果类型不一致可以拉链
    val rdd3Zip = rdd1.zip(rdd2)
    rdd3Zip.saveAsTextFile("output4")


  }
}
