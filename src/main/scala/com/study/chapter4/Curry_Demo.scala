package com.study.chapter4

object Curry_Demo {
  def main(args: Array[String]): Unit = {
    //创建两个参数的方法
    def hello(x:Int,y:Int)={
      x+y
    }
    //给方法赋值
    val result: Int = hello(10,30)
    //打印输出
    println(result)


    //柯里化
    def curry(x:Int)(y:Int)={
      x+y
    }
    //给柯里化赋值
    val result1: Int = curry(15)(20)
    //打印输出
    println(result1)

    //推导过程
    val method: Int => Int = curry(100)
    val result3: Int = method(200)
    println(result3)

    //个人推导
    def methods(x:Int)={
      //方法体里计算有匿名函数
      //方法体当中，匿名函数计算调用了方法的参数叫做闭包
      (y:Int)=>y+x
    }
    //推导
    def methods1(x:Int)(y:Int)= x+y


  }

}
