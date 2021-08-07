package com.atguigu.spark.join

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Liang on 2021/8/2.
 */
object RDDJoin {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("flatmap"))
    val rdd: RDD[(Int, String)] = sparkContext.makeRDD(Array((1, "a"), (2, "b"), (3, "c")))
    val rdd1: RDD[(Int, Int)] = sparkContext.makeRDD(Array((1, 4), (2, 5), (4, 6), (5, 3)))
    /**
    (1,(a,4))
    (2,(b,5))
     */
    println("join")
    rdd.join(rdd1).collect().foreach(println)
    println("leftjoin")

    /**
     * (1,(a,Some(4)))
     *(2,(b,Some(5)))
     *(3,(c,None))
     */
    rdd.leftOuterJoin(rdd1).collect().foreach(println)
    val dataRDD1 = sparkContext.makeRDD(List(("a", 1), ("a", 2), ("c", 3),("d", 3)))
    val dataRDD2 = sparkContext.makeRDD(List(("a", 1), ("c", 2), ("c", 3),("e", 3)))
    val value: RDD[(String, (Iterable[Int], Iterable[Int]))] =
    /**
    (a,(CompactBuffer(1, 2),CompactBuffer(1)))
    (c,(CompactBuffer(3),CompactBuffer(2, 3)))
    (d,(CompactBuffer(3),CompactBuffer()))
    (e,(CompactBuffer(),CompactBuffer(3)))
     */
      dataRDD1.cogroup(dataRDD2)

    value.collect().foreach(println)
  }
}
