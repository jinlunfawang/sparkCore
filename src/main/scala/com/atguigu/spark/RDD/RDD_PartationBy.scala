package com.atguigu.spark.RDD

import org.apache.spark._

/**
  * @Author: liangfangwei
  * @Date: 2020/9/10 14:52
  * @version 1.0
  */
object  RDD_PartationBy {

  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_GroupBy"))
    val rdd1 = sparkContext.makeRDD(List((1, "hello"), (2, "hello"), (3, "hello"), (4, "hello"), (5, "hello"), (6, "hello")), 1);
    // 数据分区 要求参数Partitioner 对象
    // 分区规则:①HashPartitioner 分区的规则是  key.hashcode/分区数

    //         ②自定义分区 继承
    // abstract class Partitioner extends Serializable {
    //  def numPartitions: Int
    //  def getPartition(key: Any): Int
    //}

    val value = rdd1.partitionBy(new HashPartitioner(2))
    val value2 = value.partitionBy(new HashPartitioner(3))


    sparkContext.stop()


  }


}
