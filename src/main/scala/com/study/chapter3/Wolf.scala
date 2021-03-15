package com.study.chapter3

import scala.beans.BeanProperty

class Wolf {
  @BeanProperty var age = 18
}

object enter2{
  def main(args: Array[String]): Unit = {
    val w = new Wolf
    //getter方法
    println(w.getAge)
    //setter方法
    w.setAge(28)
    //getter方法
    println(w.getAge)

  }
}