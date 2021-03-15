package com.study.chapter3

class Dog1(val name:String,val age:Int) {
  var color="color"
  //创建辅助构造器
  def this(name:String,age:Int,color:String){
    //首行调用主构造器或其它辅助构造器
    this(name,age)
    this.color=color
  }
}

class Cat1( name:String, age:Int,val hobby:String) extends Dog1(name, age){
  var favor="book"
  println(name)
  println(age)
  println(hobby)
  //创建子类当中的辅助构造器
  def this(name:String,age:Int,hobby:String,favor:String){
    //只能调用本类的主构造器或辅助构造器，不能调用超类的构造器
    this(name,age,hobby)
    this.favor=favor
  }
}
object enter10{
  def main(args: Array[String]): Unit = {
    val c = new Cat1("小白",3,"看大门")
  }
}