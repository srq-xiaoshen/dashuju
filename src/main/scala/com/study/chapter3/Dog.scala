package com.study.chapter3

class Dog {
  val age = 4
  def shout(content:String)={
    println(content)
  }
}

object enter{
  def main(args: Array[String]): Unit = {
    val d = new Dog
    //调用属性
    println(d.age)
    d.shout("你好")
    //通过空格调用
    println(d age)
    d shout("我是空格")
  }
}
