package com.atguigu.spark.RDD

/**
  * @Author: liangfangwei
  * @Date: 2021/6/26 11:55
  * @version 1.0
  */
object TestCaseClass {


  /**
    * 匹配对象
    */

  def main (args: Array[String]): Unit = {

    val student = Student("alice", 29)
    // 针对对象实例的内容进行匹配
    val result = student match {
      case Student("alice", 19) => "Alice, 18"
      case _ => "Else"
    }
    println(result)
  }

  // 定义类
  class Student (val name: String, val age: Int)

  // 定义伴生对象
  object Student {
    // apply 根据传入的值 构建一个对象
    def apply (name: String, age: Int): Student = new Student(name, age)

    // unapply 根据传入的对象 ,还原属性值 用做模式匹配
    def unapply (stu: Student): Option[(String, Int)] = {
      if (stu == null) {
        None
      } else {
        println(Some((stu.name, stu.age)))

        Some((stu.name, stu.age))

      }
    }
  }

}
