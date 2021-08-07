package com.atguigu.spark.RDD

/**
  * @Author: liangfangwei
  * @Date: 2020/9/10 16:28
  * @version 1.0
  */
object TestSomeAndNone {

  def main(args: Array[String]): Unit = {
    val myMap: Map[String, String] = Map("key1" -> "value")
    val value1: Option[String] = myMap.get("key1")
    val value2: Option[String] = myMap.get("key2")

    println(value1) // Some("value1") 返回的是Stirng 这个String 是什么
    println(value2) // None
  }



}
