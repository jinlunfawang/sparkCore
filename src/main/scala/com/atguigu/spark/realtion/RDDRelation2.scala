package com.atguigu.spark.realtion

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/7/31 16:50
  * @version 1.0
  */
object RDDRelation2 {

    def main(args: Array[String]): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))


      val fileRDD: RDD[String] = sparkContext.textFile("input/word.txt")
      println(fileRDD.dependencies)
      println("	")

      val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
      println(wordRDD.dependencies)
      println("	")

      val mapRDD: RDD[(String, Int)] = wordRDD.map((_,1))
      println(mapRDD.dependencies)
      println("	")

      val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_+_)
      println(resultRDD.dependencies)

      resultRDD.collect()

    }
}
