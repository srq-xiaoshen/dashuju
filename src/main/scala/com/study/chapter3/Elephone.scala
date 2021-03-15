package com.study.chapter3

abstract class Elephone {
  //没有变量值的字段就是抽象字段
  val name:String
  //没有方法体的方法就是抽象方法
  def Good():String
}

class Demo2 extends Elephone{
   val name: String = "deah"

   def Good(): String = "我是补全父类的方法"
}
object enter9{
  def main(args: Array[String]): Unit = {
    val d = new Demo2
    println(d.name)
    println(d.Good())
  }
}