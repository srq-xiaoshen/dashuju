package com.study.chapter1

object Lazy_Demo {
  //创建一个方法
  def init()={
    println("2、init方法开始执行")
    "3、小李飞刀-李寻欢"
  }
  def main(args: Array[String]): Unit = {
    //延迟初始化init方法，什么时候调用，什么时候初始化
    lazy val msg = init()
    println("1、lazy方法开始执行")
    println(msg)
  }

}
