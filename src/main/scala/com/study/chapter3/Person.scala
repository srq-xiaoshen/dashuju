package com.study.chapter3

class Person {
  var name="zhangsan"
  val age =18
  //伴生类和伴生对象当中可以调用
  private val hobby = "read"
  //private[this]修饰，伴生类和伴生对象当中也不可以调用，只能在类的内部使用
  private[this] val color = "black"
  //如果没有想好变量值，可用_代替，但是变量类型必须补全
  var address:String=_
  def hello={
    println(color)
  }
}

object Person{
  def main(args: Array[String]): Unit = {
    val p = new Person
    //调用name
    println(p.name)
    //调用age
    println(p.age)
    //调用hobby
    println(p.hobby)
    p.hello
  }
}