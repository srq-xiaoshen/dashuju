package com.study.chapter3

class Duck {
  val name ="dabaine"
  def Bye()={
    println("我是父类当中非抽象方法")
  }
}

class Snake extends Duck{
  //重写父类当中的非抽象属性必须加关键字
  override val name: String = "小黄鸭"
  //重写父类当中非抽象方法必须加关键字
  override def Bye(): Unit = {
    println("我是重写父类的方法")
  }
  //子类的方法调用父类的方法
  def sayByeBye()=super.Bye()

}
object  enter7{
  def main(args: Array[String]): Unit = {
    val s = new Snake
    println(s.name)
    s.Bye()
    s.sayByeBye()
  }
}
