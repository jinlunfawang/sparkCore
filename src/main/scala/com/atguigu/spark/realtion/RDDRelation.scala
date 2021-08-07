package com.atguigu.spark.realtion

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei
  * @Date: 2021/7/31 16:50
  * @version 1.0
  */
object RDDRelation {

    def main(args: Array[String]): Unit = {
      val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
      val fileRDD: RDD[String] = sparkContext.textFile("input/word.txt")

      /**
        * (3) input/word.txt MapPartitionsRDD[1] at textFile at RDDRelation.scala:15 []
        *  input/word.txt HadoopRDD[0] at textFile at RDDRelation.scala:15 []
        */
      println(fileRDD.toDebugString)
      println("	")
      val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))

      /**
        * (3) MapPartitionsRDD[2] at flatMap at RDDRelation.scala:19 []
        * |  input/word.txt MapPartitionsRDD[1] at textFile at RDDRelation.scala:15 []
        * |  input/word.txt HadoopRDD[0] at textFile at RDDRelation.scala:15 []
        */
      println(wordRDD.toDebugString)
      println("	")
      val mapRDD: RDD[(String, Int)] = wordRDD.map((_,1))
      /**
        * (3) MapPartitionsRDD[3] at map at RDDRelation.scala:23 []
        * |  MapPartitionsRDD[2] at flatMap at RDDRelation.scala:19 []
        * |  input/word.txt MapPartitionsRDD[1] at textFile at RDDRelation.scala:15 []
        * |  input/word.txt HadoopRDD[0] at textFile at RDDRelation.scala:15 []
        */
      println(mapRDD.toDebugString)
      println("	")

      /**
        * (3) ShuffledRDD[4] at reduceByKey at RDDRelation.scala:27 []
        * +-(3) MapPartitionsRDD[3] at map at RDDRelation.scala:23 []
        * |  MapPartitionsRDD[2] at flatMap at RDDRelation.scala:19 []
        * |  input/word.txt MapPartitionsRDD[1] at textFile at RDDRelation.scala:15 []
        * |  input/word.txt HadoopRDD[0] at textFile at RDDRelation.scala:15 []
        */
      val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_+_)
      println(resultRDD.toDebugString)
      resultRDD.collect()
  }
}
