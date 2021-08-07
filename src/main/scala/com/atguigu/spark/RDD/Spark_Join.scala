package com.atguigu.spark.RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/7/5 16:40
  * @version 1.0
  */
object Spark_Join {
  def main (args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""));

    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (2, "b"), (3, "c")))
    val rdd1: RDD[(Int, Int)] = sc.makeRDD(Array((1, 4), (2, 5), (3, 6),(5,3)))

    /**
      * (1,(a,4))
      * (2,(b,5))
      * (3,(c,6))
      */
    rdd.join(rdd1).collect().foreach(println)

  }
}
