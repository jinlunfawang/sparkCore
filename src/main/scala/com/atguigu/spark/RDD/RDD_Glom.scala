package com.atguigu.spark.RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 算所有分区最大值求和（分区内取最大值，分区间最大值求和
  */

object RDD_Glom {
  def main (args: Array[String] ): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
    val dataRDD = sparkContext.makeRDD(List(1,2,3,4),2)
    var a:Int=0
    // 取出每个分区的最大值 每个值为一个数组
    val dataRDD1:RDD[Array[Int]] = dataRDD.mapPartitions(x=>{
      List(x.max).iterator
    }).glom()
    //返回数组中的值
    val dataRDD2 = dataRDD1.map(array => {
      array(0)
    })
    // 数组值求和
   val array:Array[Int]= dataRDD2.collect()
    println(array.sum)
}
}
