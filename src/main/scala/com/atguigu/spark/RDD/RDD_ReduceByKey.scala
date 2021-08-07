package com.atguigu.spark.RDD

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 统计不同小时段的apache.log
  *
  */

object RDD_ReduceByKey {
  def main (args: Array[String] ): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_GroupBy"))
    val rdd1 = sparkContext.makeRDD(List((1,2),(1,3),(2,5),(2,6),(2,7),(3,7)),5);
    //1 (1,2)
    //2 (1,3)
    //3 (2,5)
    //4  2 6
    //5 27 37
    // 根据key分组 再根据value聚合
     var rdd2=rdd1.reduceByKey(_+_);
     rdd2.saveAsTextFile("output7")
    sparkContext.stop()

}
}
