package com.study.chapter4

object High_Function {
  def main(args: Array[String]): Unit = {
    //创建匿名函数
    val fun1 =(x:Int)=>{
      x+1
    }
    //创建一个参数列表为函数的匿名函数
    val fun2=(f1:Int=>Int)=>{
      f1(1)+124
    }
    //创建两个参数的匿名函数
    val fun3 =(f1:Int=>Int,y:Int)=>{
      f1(5)
    }

    //调用函数
    val result: Int = fun1(1)
    println(result)
    //调用fun2
    val result1: Int = fun2(fun1)
    println(result1)
    //调用fun3
    val result2: Int = fun3(fun1,5)
    println(result2)
  }

}
