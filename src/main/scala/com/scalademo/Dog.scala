package com.scalademo

class Dog( val name: String, val age: Int)  {
    println("主构造器执行")
  private var gender="aaa"
  def this(name :String,age: Int,gender:String){
    this(name,age)
    this.gender=gender
    println("辅助构造器执行")
  }
}
class Dogg(name:String,age:Int) extends Dog(name:String,age :Int){
    def this(name: String,age: Int,id: Int){
      this(name,age)
    }
}
