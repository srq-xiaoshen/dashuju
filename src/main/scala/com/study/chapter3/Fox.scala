package com.study.chapter3

class Fox {
  val name = "dabai"
  def hello()={
    println("我是父类当中的方法")
  }
}

class Fish extends Fox {
  val age = 12
  def sayHello()={
    println("我是子类当中的方法")
  }
}

object enter6{
  def main(args: Array[String]): Unit = {
    val f = new Fish
    f.hello()
    println(f.name)
    println(f.age)
    f.sayHello()
  }
}
