package com.study.chapter3

trait Log {
  //非抽象字段
  val name = "lisi"
  //非抽象方法
  def hello()={
    println("我可是特质当中非抽象方法")
  }
  //抽象字段
  val age:Int
  //抽象方法
  def world()
}

trait Url{
  val hobby="running"
  def study()={
    println("我是第二个特质当中的方法")
  }
}
trait hu{
  def play: Unit ={
    println("我是第三个特质当中的方法")
  }
}
class Demo7 extends Log with Url with hu {
  override val age: Int = 20

  override def world(): Unit = {
    println("我是补全后的方法")
  }
}

object Enter3{
  def main(args: Array[String]): Unit = {
    val d = new Demo7
    println(d.hobby)
    d.study()
    d.play
  }
}