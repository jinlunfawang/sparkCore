package com.atguigu.test

/**
 * Created by Liang on 2021/8/2.
 */
object TupelSort {
  def main(args: Array[String]): Unit = {
    val list = List(5,1,8,2,-3,4)
    val list3 = List(5,1,8,2,-3,4)

    val list2 = List((1, 5), (2, 1), (3, 8), (6, 2), (4, -3), (9, 4))

    list2.sorted
    // 5.2 sortBy 指定排序字段
    println(list2.sortBy(tuple=>tuple))
    println(list2.sortBy(_._2))
    println(list2.sortBy(_._2)(Ordering[Int].reverse))
  }
}
