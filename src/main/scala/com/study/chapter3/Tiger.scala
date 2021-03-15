package com.study.chapter3

class Tiger {
  //有getter方法，没有setter方法
  val name = "xiaobai"
  //既有getter，也有setter
  var leg =4
}

object enter1{
  def main(args: Array[String]): Unit = {
    //创建对象
    val t = new Tiger
    //获取val修饰的变量
    println(t.name)
    //var修饰
    //setter方法
    //leg_=()
    t.leg_= (10)
    //getter方法
    println(t.leg)
  }
}
