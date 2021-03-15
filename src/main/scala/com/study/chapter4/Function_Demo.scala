package com.study.chapter4

object Function_Demo {
  def main(args: Array[String]): Unit = {
    //匿名函数：使代码更加简介
    val fun1=(x:Int)=>{
      x+1
    }
    val fun2=(x:Int,y:Int)=>{
      x+y
    }

    //函数也是对象
    /*val result = new Function0[Int] {
      override def apply(): Int = {
        12
      }
    }
    //打印输出
    println(result())
*/

    //创建一个参数的函数
    /*val result =  new Function[Int,Int] {
      override def apply(v1: Int): Int = {
        v1
      }
    }
    println(result(100))
    //匿名函数简化写法
    val fun:(Int)=>Int={
      x=>120
    }*/


    //创建两个参数的函数
    /*val result = new Function2[Int,Int,Int] {
      override def apply(v1: Int, v2: Int): Int = {
        v1+v2
      }
    }
    println(result(12, 12))
    //通过匿名函数简化写法
    val fun:(Int,Int)=>Int={
      (x,y)=>x+y
    }
    println(fun(10, 10))*/
















  }

}
