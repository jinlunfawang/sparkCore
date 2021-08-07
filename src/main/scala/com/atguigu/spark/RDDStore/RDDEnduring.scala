package com.atguigu.spark.RDDStore

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/8/1 11:45
  * @version 1.0
  */
object RDDEnduring {
  def main (args: Array[String]): Unit = {
    var sc = new SparkContext(new SparkConf().setAppName("").setMaster("local[*]"))
    /**
      * wordCount
      */
   var rdd1= sc.makeRDD(List("hello world","hello spark","flink"),2)
    // reduceBy  分区内会预聚合
    var rdd2= rdd1.flatMap(_.split(" ")).map((_,1)).aggregateByKey(1)(_+_,_+_)
      rdd2.collect().foreach(println(_))

  }
}
