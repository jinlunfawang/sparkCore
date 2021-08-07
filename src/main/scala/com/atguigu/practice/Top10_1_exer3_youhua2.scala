package com.atguigu.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Top10 热门品类
 * 需求：统计top10的品类
 * 要求: 首先按照点击数排序 如果点击数相同按照下单数排序,
 * 例子：
 * 鞋	  点击数 下单数	支付数
 * 衣服	点击数 下单数	支付数
 *
 * 优化的点：
 * 在优化1的基础上 一开始处理数据就将数据转换为最终的那种形式
 *
 * (id,(1,0,0))
 * (id,(0,1,0))
 * (id,(0,0,1))
 * reduceBykey（三个值相加）
 *
 *
 */
object Top10_1_exer3_youhua2 {
  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis"))
    //1 读取数据
    var actionRDD: RDD[String] = sparkContext.textFile("input2/user_visit_action.csv")
    //2. 包装数据为指定格式  (id,(1,0,0)) (id,(0,1,0)) (id,(0,0,1))  测试git提交
    val flatRDD: RDD[(String, (Int, Int, Int))] = actionRDD.flatMap(
      action => {
        val datas = action.split(",")
        if (datas(6) != "-1") {
          // 点击的场合
          List((datas(6), (1, 0, 0)))
        } else if (datas(8) != "null") {
          // 下单的场合
          val ids = datas(8).split("-")
          ids.map(id => (id, (0, 1, 0)))
        } else if (datas(10) != "null") {
          // 支付的场合
          val ids = datas(10).split("-")
          ids.map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      }
    )

    // 3. 将相同的品类ID的数据进行分组聚合
    //    ( 品类ID，( 点击数量, 下单数量, 支付数量 ) )
    val analysisRDD = flatRDD.reduceByKey(
      (t1, t2) => {
        ( t1._1+t2._1, t1._2 + t2._2, t1._3 + t2._3 )
      }
    )

    // 4. 将统计结果根据数量进行降序处理，取前10名
    val resultRDD = analysisRDD.sortBy(_._2, false).take(10).foreach(println)
    /*actionRDD.flatMap(line => {
      val datas: Array[String] = line.split(",")
      if (datas(6) != "-1") {
        List((datas(6), (1, 0, 0)))
      } else if (datas(8) != "null") {
        datas(8).split("-").map((_, (0, 1, 0)))
      }
      else if (datas(10) != "null") {
        datas(10).split("-").map((_, (0, 0, 1)))
      }
      else {
        Nil
      }
    }).reduceByKey((t1,t2)=>{(t1._1+t2._1,t1._2+t2._2,t1._3+t2._3)})
      .sortBy(_._2,false)
      .take(10).foreach(println)*/
  }

}
