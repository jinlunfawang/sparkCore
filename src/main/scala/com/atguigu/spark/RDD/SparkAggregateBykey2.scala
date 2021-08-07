package com.atguigu.spark.RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 相同key出现的平均次数
  * 总次数/相同key合集频次
  *
  */
object SparkAggregateBykey2 {
  def main (args: Array[String]): Unit = {
    var sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("AggregatrByKey2"))
    val result: RDD[(String, Int)] = sc.makeRDD(
      List(
        ("a", 1), ("a", 2), ("b", 3),
        ("b", 4), ("b", 5), ("a", 6)
      ), 2)
    //1.返回结果为 :key,(总次数,合计的频次)
    //1.1 分区内合并
    // 1.2 分区间合并

    var newRDD: RDD[(String, (Int, Int))] = result.aggregateByKey((0, 0))((t, v) => (t._1 + v, t._2 + 1), (t1, t2) => (t1._1 + t2._1, t1._2 + t2._2))
    newRDD.mapValues{case (a,b)=> {a/b}}.collect().foreach(println)


    // 2.平均值=总次数/合计的频次


  }
}
