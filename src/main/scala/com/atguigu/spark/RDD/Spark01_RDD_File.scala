package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: liangfangwei 从文件中读取数据
  * @Date: 2020/8/8 19:31
  * @version 1.0
  */
object Spark01_RDD_File {

  def main(args: Array[String]): Unit = {
    val conf = new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""));
     // 实际3个分区文件
    /***
      * 分区数量:
      *   总字节/minPartitions=分区数.....余数
      *   余数 > 单个切片的10% 再分一个区
      * 共 7 字节/3=2.....1
      * 剩余1kb 1>2*10% 所有再切一片
      *总共 4片
      *  分区内的数据:
      *  每次至少读1行+offset偏移量

      *          数据:
      *
      *0分区:       1@ 2字节   [0,0+2]   12
      *1分区:       2@        [2,2+2]    3
      *2分区:       3@        [4,4+2]     4
      *3分区:       4@

      */
    val value1 = conf.textFile("input/word.txt", 3)

    value1.saveAsTextFile("output1")
    conf.stop()

  }
}
