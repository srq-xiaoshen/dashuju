package com.study.chapter3

//与类交织在一起就是主构造器
//没有val/var修饰，传进去的只是参数
//有val/var修饰，升级为属性
class Cat private(val name:String,val age:Int=20) {
  println(name)
  println(age)
  //声明变量
  var color:String = "color"
  //创建辅助构造器
  //this关键字创建
  def this(name:String,age:Int,color:String){
    //辅助构造器首行必须调用主构造器或其它辅助构造器
    this(name,age)
    this.color=color
    println(color)
  }
}

object Cat{
  def main(args: Array[String]): Unit = {
    val c = new Cat("xiaohua")
  }
}