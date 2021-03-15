package com.study.chapter2

//import scala.collection.immutable.HashMap
import scala.collection.mutable._

object Map_Practice {
  def main(args: Array[String]): Unit = {
    /*//var map1 = new mutable.HashMap[String, String]()


    /*map1 += ("ML" -> "hadoop");
    map1 += ("DL" -> "spark");
    var map11 = map1
    map1 += ("ML" -> "Neural NetWork");
    println("=============mutable 可变的================")
    println(map1.mkString("、"))
    println(map11.mkString("、"))
*/
    var map2 = new HashMap[String, String]()
    map2 = map2 + ("ML" -> "hadoop");
    map2 = map2 + ("DL" -> "spark");
    var map22 = map2
    map2 += ("ML" -> "Neural NetWork");
    println("=============immutable 不可变的================")
    println(map2.mkString("、"))
    println(map22.mkString("、"))*/


    val map = Map("songjiang"->13,"lujunyi"->99)
    //修改key对应的value
    map("songjiang")=76
    //打印输出
    println(map)

  }

}
