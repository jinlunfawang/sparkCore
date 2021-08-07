package com.atguigu.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Top10 热门品类
 * 需求：统计top10的品类
 * 要求: 首先按照点击数排序 如果点击数相同按照下单数排序,
 * 例子：
 * 鞋	点击数 下单数	支付数
 * 衣服	点击数 下单数	支付数
 * 实现思路
 * 1.依次分别求出每个类别的 点击数 下单数 支付数
 * 鞋子 点击数
 * 鞋子 下单数
 * 鞋子 支付数
 * 2.按照类别id关联
 * 3.（类别id,(点击数 下单数 支付数)） 按照value 降序排序
 *
 *
 */
object Top10_1_exer2 {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis"))
    //1 切分数据
    var inputData: RDD[String] = sparkContext.textFile("input2/user_visit_action.csv")
    // 2 分类统计
    // 2.1商品ID 点击数量
    val clickCount = inputData.filter(line => {
      val strings = line.split(",")
      strings(6) != "-1"
    }).map(line => {
      val data = line.split(",")
      (data(6), 1)
    }).reduceByKey(_ + _);

    // 2.2 商品id 下单数量
    val orderCount = inputData.filter(line => {
      val data = line.split(",")
      data(8) != "null"
    }).flatMap(line => {
      val strings = line.split(",")
      strings(8).split("-").map( (_, 1))
    }).reduceByKey(_ + _)

    //2.3 商品ID 支付数量
    val payCount = inputData.filter(line => {
      val data = line.split(",")
      data(10) != "null"
    }).flatMap(line => {
      var data = line.split(",")
      data(10).split("-").map((_, 1))
    }).reduceByKey(_ + _)

    //3 根据ID关联
    val fulloinResult: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = clickCount.cogroup(orderCount, payCount)
    val result=fulloinResult.mapValues{case (clickIter,orderIter,payIter)=>{
      val clickIterator: Iterator[Int] = clickIter.iterator
      var clickCount=0
        if (clickIterator.hasNext) {
           clickCount = clickIterator.next()
        }
      val orderIterator = orderIter.iterator
      var orderCount=0
      if (orderIterator.hasNext) {
        orderCount=orderIterator.next()
      }
      val payIterator = payIter.iterator
      var payCount=0
      if (payIterator.hasNext) {
        payCount = payIterator.next()
      }
      (clickCount,orderCount,payCount)
    }}

    //4 排序取前10
    val tuples = result.sortBy(_._2, false).take(10)
    tuples.foreach(println)

  }
}
