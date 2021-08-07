package com.atguigu.spark.RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 截取日志文件的路径
  */
object RDD_MapLog {
  def main (args: Array[String] ): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("getLogUrl"))
    val rdd:RDD[String] = sparkContext.textFile("input/apache.log")

    val rdd1:RDD[String] =   rdd.map(line=>{
      val strings:Array[String] = line.toString.split(" ");
      strings(6)
    })
    println(rdd1.collect().mkString("\n"))
}
}
