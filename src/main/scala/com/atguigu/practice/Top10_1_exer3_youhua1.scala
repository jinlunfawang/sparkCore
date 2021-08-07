package com.atguigu.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Top10 热门品类
 * 需求：统计top10的品类
 * 要求: 首先按照点击数排序 如果点击数相同按照下单数排序,
 * 例子：
 * 鞋	点击数 下单数	支付数
 * 衣服	点击数 下单数	支付数
 *
 * 优化的点：
 * 1.数据源重复使用 -- 使用cache
 * 2.cogroup 可能存在shuffer
 * (id,(点击，0，0))
 * (id (0,订单数,0))
 * (id,(0,0,支付数))
 * Union 为一个数据源
 * reduceBykey（三个值相加）
 *
 *
 */
object Top10_1_exer3_youhua1 {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis"))
    //1 切分数据
    var inputData: RDD[String] = sparkContext.textFile("input2/user_visit_action.csv")
    // 存储
    inputData.persist(StorageLevel.MEMORY_ONLY)
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
    var value1= clickCount.mapValues(action=>(action,0,0))
    var value2=orderCount.mapValues(action=>(0,action,0))
    var value3=payCount.mapValues(action=>(0,action,0))
     var result=  value1
         .union(value2)
         .union(value3)
         .reduceByKey((t1,t2)=>(t1._1+t2._1,t1._2+t2._2,t1._3+t2._3))
         .sortBy(_._2,false)
         .take(10)
         .foreach(println)


  }
}
