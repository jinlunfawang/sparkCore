package com.atguigu.spark.RDD

/**
  * @Author: liangfangwei
  * @Date: 2020/9/10 16:28
  * @version 1.0
  */
object TestCase {

  def main(args: Array[String]): Unit = {
    //泛型擦除
    println(describe(List(1, 2, 3, 4, 5)))
    //数组例外，可保留泛型
    println(describe(Array(1, 2, 3, 4, 5, 6)))
    println(describe(Array("abc")))
  }
  def describe(x: Any) = x match {

    case i: Int => "Int"
    case s: String => "String hello"
    case m: List[_] => "List"
    case c: Array[Int] => "Array[Int]"
    case someThing => "something else " + someThing
  }



}
