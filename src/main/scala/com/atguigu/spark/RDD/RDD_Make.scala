package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/6/23 15:07
  * @version 1.0
  *          创建RDD的四种方式
  *          1.从内存中创建
  *          2.从文件中创建
  *          3. 从其他RDD创建
  *          4.直接new 框架使用的多
  */
object RDD_Make {
  def main (args: Array[String]): Unit = {

   // TODO 1.创建spark运行环境  local[4] 设置4个核心 local[*] 当前环境所有核心数
    var sc=new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Create"))
    //TODO 2.从内存中创建RDD
    val rdd = sc.makeRDD(List(1,2,3,4),2)
    val rddMap = rdd.map(_*2)
     rddMap.collect().foreach(println(_))


  }
}
