package com.study.chapter4

object Demo {
  def main(args: Array[String]): Unit = {
    def ttt(f:Int=>Int)={
      f(10)
    }
    val fun1=(x:Int)=>{
      x*10
    }
    def method(x:Int)={
      x*10
    }
    //函数作为参数传递到方法中去
    val result1: Int = ttt(fun1)
    println(result1)
    //将method作为参数传递到ttt里面
    //将方法转换为函数
    val fun2: Int => Int = method _
    val result2: Int = ttt(fun2)
    println(result2)
    //将方法传入
    val result3: Int = ttt(method _)
    println(result3)
    //将方法传入
    val result4: Int = ttt(method)
    println(result4)
    //将方法传入
    val result5: Int = ttt(x=>method(x))
    println(result5)
  }
}
