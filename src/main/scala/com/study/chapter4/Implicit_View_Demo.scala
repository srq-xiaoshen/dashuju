package com.study.chapter4

object Implicit_View_Demo {
  def main(args: Array[String]): Unit = {
    implicit def douToInt(x:Double)={
      x.toInt
    }
    val a:Int=3.5
    println(a)
  }

}
