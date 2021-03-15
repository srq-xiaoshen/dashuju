package com.study.chapter3

abstract class Demo3 {
  val name ="heer"
  var age:Int
  def sayNiHao()={
    println("我是父类方法")
  }
}

class Demo5 extends Demo3{
  override var age: Int = 12
  override val name: String = "李寻欢"

  override def sayNiHao(): Unit = println("小李飞刀")
}

object Enter{
  def main(args: Array[String]): Unit = {
    val d = new Demo5
    println(d.age)
    d.sayNiHao()
  }
}