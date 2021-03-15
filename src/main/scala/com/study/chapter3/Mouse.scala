package com.study.chapter3

class Mouse(name:String,age:Int) {
  var hobby ="tou"
  //创建辅助构造器
  def this(name:String,age:Int,hobby:String){
    //首行调用主构造器或其它辅助构造器
    this(name,age)
    this.hobby=hobby
  }
}

object Mouse{
  def apply(name: String, age: Int): Mouse = {
    println("我是两个参数")
    new Mouse(name, age)
  }

  def apply(name: String, age: Int, hobby: String): Mouse = {
    println("我是三个参数")
    new Mouse(name, age, hobby)
  }

  def main(args: Array[String]): Unit = {
    val m = Mouse("xiaohe",93,"touyumi")

  }
}
