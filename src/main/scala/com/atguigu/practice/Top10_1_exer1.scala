package com.atguigu.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Top10 热门品类
 * 需求：求每个类别的的top10
 * 要求 首先按照点击数排序 如果点击数相同按照下单数排序
 * 例：
 * 鞋	点击数 下单数	支付数
 * 衣服	点击数 下单数	支付数
 * 实现思路
 * 1.依次分别求出每个类别的点击数 下单数 支付数
 * 鞋子 点击数
 * 鞋子 下单数
 * 鞋子 支付数
 * 2.full join
 * 鞋子 （iter(点击数1,2,3) iter(下单数,2,3) iter(支付数1,2,3)）
 *
 * @Author‰: liangfangwei
 * @Date: 2021/8/1 21:25
 * @version 1.0
 */
object Top10_1_exer1 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis"))
      //1 切分数据
    var inputData: RDD[String] = sc.textFile("input2/user_visit_action.csv", 2)
    // 2.统计点击数量（品类id 点击数量）

    val value: RDD[String] = inputData.filter(action => {
      val datas = action.split(",")
      datas(6) != "-1"
    })
    val value1: RDD[(String, Int)] = value.map(action => {
      val datas: Array[String] = action.split(",")
      (datas(6), 1)
    }).reduceByKey(_ + _)
    //3.统计商品下单（品类id 下单数量）
    val value2: RDD[(String, Int)] = inputData.filter(line => {
      val datas = line.split(",")
      datas != "null"

    }).flatMap(action => {
      val datas = action.split(",")
      val cids = datas(8).split("-")
      cids.map(id => (id, 1))
    }).reduceByKey(_ + _)

    // 4 (品类id ,支付数量)
    val value3: RDD[(String, Int)] = inputData.filter(line => {
      val strings: Array[String] = line.split(",")
      strings(10) != "null"
    }).flatMap(line => {
      val datas = line.split(",")
      datas(10).split("-").map((_, 1))
    })
    val value4: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = value1.cogroup(value2, value3)
    val analusisRdd = value4.mapValues { case (clickIter, oderIter, payIter) => {
      var clickCnt = 0;
      if (clickIter.iterator.hasNext) {
        clickCnt = clickIter.iterator.next()
      }
      var orderCnt = 0;
      if (oderIter.iterator.hasNext) {
        orderCnt = oderIter.iterator.next()
      }
      var payCnt = 0;
      if (payIter.iterator.hasNext) {
        payCnt = payIter.iterator.next()
      }
      (clickCnt, orderCnt, payCnt)
    }
    }
    val result = analusisRdd.sortBy(_._2, false).take(10)
    result.foreach(println)

  }
}
