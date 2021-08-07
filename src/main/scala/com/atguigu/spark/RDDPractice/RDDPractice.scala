package com.atguigu.spark.RDDPractice

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  *
  * agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔。
  * 需求描述
  * 统计出每一个省份每个广告被点击数量排行的Top3
  *
  * @Author: liangfangwei
  * @Date: 2021/7/6 21:08
  * @version 1.0
  */
  object RDDPractice {
    def main (args: Array[String]): Unit = {
      val sc = new SparkContext(new SparkConf().setAppName("RDDPractice").setMaster("local[*]"))
      val in: RDD[String] = sc.textFile("input/agent.log")
      // 1. map 读取为什么是一行数据
      var mapRDD: RDD[((String, String), Int)] = in.map(line => {
        val strings: Array[String] = line.split(" ")
        ((strings(1), strings(4)), 1)
      })
      //2.((省份,广告),1) =>聚合=>((省份,广告),sum)=>按照省份分组==>(省份,(广告,sum))==>排序==> 排序取前三
      // map()  和 map{ } 区别
      val groupByRdd: RDD[(String, Iterable[(String, Int)])] = mapRDD.reduceByKey(_ + _).map { case ((pro, ad), sum) => {
        (pro, (ad, sum))
      }
      }.groupByKey()
      // 为什么可以这样写 _._2
      val tuples = groupByRdd.mapValues(iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse)
          .take(3)
      })
      tuples.collect().foreach(println)


    }

}
