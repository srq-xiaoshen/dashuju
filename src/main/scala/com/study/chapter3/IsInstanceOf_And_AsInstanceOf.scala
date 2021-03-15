package com.study.chapter3

object IsInstanceOf_And_AsInstanceOf {
  def main(args: Array[String]): Unit = {
    var map01 = Map[String,Any]("name"->"张三","gender"->'男',"bhtday"->1998)

    println(map01("name").isInstanceOf[String])
    println(map01("gender").isInstanceOf[Char])
    println(map01("bhtday").isInstanceOf[Int])
    println(map01("name").asInstanceOf[String])
    println(map01("gender").asInstanceOf[Char])
    println(map01("bhtday").asInstanceOf[Int])
  }

}
