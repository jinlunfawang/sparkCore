package com.atguigu.spark.RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 分区内求最大值 分区间相同的key求和
  * @Author:liangfangwei
  * @Date:2021 /6/30 10:51
  * @version 1.0
  */
object SparkAggregateBykey {
  def main (args: Array[String]): Unit = {
    var sc = new SparkContext(new SparkConf().setMaster("local[1]").setAppName("AggregatrByKey"))
    val result: RDD[(String, Int)] = sc.makeRDD(
      List(
        ("a", 1), ("a", 2), ("b", 3),
        ("b", 4), ("b", 5), ("a", 6)
      ), 2).distinct()

    /**
      * 0 分区  ("a", 1), ("a", 2), ("b", 3),
      * *                                    =====>(b,8),(a,8)
      * * 1 分区 ("b", 4), ("b", 5), ("a", 6)
      * * aggregateByKey：
      * * 第一个参数:相同key集合的初始value
      * * 第二个参数:分区内相同key集合聚合规则
      * * 第三个参数:分区间相同key集合聚合规则
      * math.max(_,_):第一个_ 表示：初始值,也就是上次合计的结果 第二个_表示:相同key准备计算的value
      */
    val str: RDD[(String, Int)] = result.aggregateByKey(10)((x, y) => math.max(x, y), _ + _);
    val str1 = str.collect().mkString(",")
    println(str1)
    println("______________________")
    /**
      * 获取相同数据key的平均值
      * 总数/次数
      * 1.分区内
      * 2.分区间
      * ("a", 1), ("a", 2), ("b", 3),
      * ("b", 4), ("b", 5), ("a", 6)
      * (相同key累加次数,相同key出现的次数))
      * t：表示上次结果 作为新的初始值
      * v:表示数据中value
      */
    val newRDD: RDD[(String, (Int, Int))] = result.aggregateByKey((0, 0))((t, v) => {
      (t._1 + v, t._2 + 1)
    }, (t1, t2) => {
      (t1._1 + t2._1, t1._2 + t2._2)
    })
    newRDD.mapValues { case (num, count) => {
      num / count
    }
    }.collect().foreach(println)


  }
}
