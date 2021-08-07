package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 随机数抽取
  * 参数1:是否放回
  * 参数2:被抽中的概率 2：表示被抽中的两次 至于一定能被抽中两次不一定 这只是表示概率
  * 参数3:随机数种子,随机算法都要一个初始值，如果没有一个初始值，
  * 他也不能凭空制造一系列的随机数出来，那我们说的随机种子seed（）就是这是初始值。
  * 默认初始值是系统时间
  */

object RDD_Smaple {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
    val value1 = sparkContext.makeRDD(List(1, 2, 3, 4, 5, 7, 8), 1)
    println(value1.sample(true, 2, 3).collect().mkString(","))

  }
}
