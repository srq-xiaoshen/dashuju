package com.study.chapter3

class Pig(name:String) {
  var age =3
  //创建辅助构造器
  def this(name:String,age:Int){
    //辅助构造器首行调用主构造器或其它辅助构造器
    this(name)
    this.age=age
  }
}

object Pig{
  //主构造器的apply方法
  def apply(name: String): Pig = {
    println("我是一个参数")
    new Pig(name)
  }
  //辅助构造器的apply方法
  def apply(name: String, age: Int): Pig = {
    println("我是两个参数")
    new Pig(name, age)
  }

  def main(args: Array[String]): Unit = {
    val p = Pig("小胖",5)

  }

}
