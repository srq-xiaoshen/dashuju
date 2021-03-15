package com.study.chapter3

class Sheep {
  val age = 19
  private val name = "如花"
  def printName()={
    println(name+Sheep.cons)
  }
}

object Sheep{
  private val cons ="看大门"
  def main(args: Array[String]): Unit = {
    val s = new Sheep
    println(s.name)
    println(s.age)
    s.printName()
  }
}