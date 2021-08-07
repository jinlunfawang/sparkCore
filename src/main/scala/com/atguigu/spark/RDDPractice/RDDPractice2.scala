package com.atguigu.spark.RDDPractice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/7/6 21:11
  * @version 1.0
  *
  *
  *agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔。
  *          需求描述
  *          统计出每一个省份每个广告被点击数量排行的Top3
  *          1.<（省份,广告）.1>
  *          2.<（省份,广告）.sum>
  *          3.< 省份,(广告，sum))
  */
object RDDPractice2 {
  private val BLANK: String = " "

  def main (args: Array[String]): Unit = {
    val sc: SparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""))
    val in: RDD[String] = sc.textFile("input/agent.log")
    val mapRDD: RDD[((String, String), Int)] = in.map(line => {
      val strings: Array[String] = line.split(BLANK)
      ((strings(1), strings(4)), 1)
    })
    val reduceRDD: RDD[((String, String), Int)] = mapRDD.reduceByKey(_ + _)
    val groupByKey: RDD[(String, Iterable[(String, Int)])] = reduceRDD.map { case ((pro, ad), sum) => (pro, (ad, sum)) }.groupByKey()
    val result: RDD[(String, List[(String, Int)])] = groupByKey.mapValues(iter=>iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(3))
    result.collect().foreach(println)

  }

}
