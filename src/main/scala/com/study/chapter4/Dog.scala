package com.study.chapter4

class Dog {
  def shout(name:String)={
    println(name+"看大门")
  }
}

class Cat{
  def catchMouse(name:String)={
    println(name+"抓老鼠")
  }
}
object Untils{
  implicit def dogToCat(dog: Dog):Cat={
    new Cat
  }
  implicit def demo(dog: Dog):Cat={
    new Cat
  }
}
object Enter{
  def main(args: Array[String]): Unit = {
    //通过import关键字调用
    //工具类当中无论有多少个方法，为了防止耦合，可以不用_，直接定位到方法
    import Untils.dogToCat
    val d = new Dog
    //调用方法
    d.shout("小灰")
    //让狗抓老鼠
    d.catchMouse("小灰")
  }
}
