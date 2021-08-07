package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2020/8/8 19:31
  * @version 1.0
  */
object Spark01_RDD_Memeoy {

  def main(args: Array[String]): Unit = {
    val context = new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""));
    // 创建sparkRdd的方式 numSlices 是分几个区  默认是当前环境的总核心核心数
    val value1 = context.makeRDD(List("1", "2", "3","4"),8)
    val value2 = context.makeRDD(List("1", "2", "3","4"),3)
    /**         startIndex endIndex    Array.slice
      *  (0 3)   0          1           array[0]=1
      *  (1 3)   1          3          array[1]=2 array[2]=3
      *  (2 3)   3           5          array[3]=4 array[4]=5
      *
      *
      */
    val value3 = context.makeRDD(List("1", "2", "3","4","5"),3)
    //value.collect().foreach(println)
    // 保存到文件中
    value1.saveAsTextFile("output1")
    value2.saveAsTextFile("output2")
    value3.saveAsTextFile("output33")
    context.stop()



  }
}
