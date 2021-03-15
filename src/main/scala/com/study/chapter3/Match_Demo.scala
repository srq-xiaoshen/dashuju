package com.study.chapter3

import scala.util.Random
//多例样例类
case class People(name:String,age:Int)
case class Time(time:Long)
//单例样例类
case object CheckOut
object Match_Demo {
  def main(args: Array[String]): Unit = {

    //匹配字符串
    /*val arr = Array("a","b","c","d","e","f","g")
    //随机获取数组当中的一个元素
    val result: String = arr(Random.nextInt(arr.length))
    //通过result接收的元素去匹配
    result match {
      case "a" => println(result+"匹配了a")
      case "b" => println(result+"匹配了b")
      case "c" => println(result+"匹配了c")
      //为了防止匹配穿透，可用_接收所有
      case _   => println("something else")
    }*/

    /*//匹配数据类型
    val arr = Array(12,"hello",3.5,true)
    //随机获取元素
    val result: Any = arr(Random.nextInt(arr.length))
    //匹配类型
    result match {
      case in:Int => println(result+"匹配到了Int类型")
      case str:String => println(result+"匹配到了String类型")
      case dou:Double =>println(result+"匹配到了Double类型")
      case _ => println(result+"匹配到了boolean类型" )
    }*/

    /*val arr = Array(1, 3, 5)
    arr match {
      case Array(1, x, y) => println(x + " " + y)
      case Array(0) => println("only 0")
      case Array(0, _*) => println("0 ...")
      case _ => println("something else")
    }*/
    //_* 表示0个或者多个元素的通配符

    /*val lst = List(3, -1)
    lst match {
      case 0 :: Nil => println("only 0")
      case _ :: Nil => println("List 只有一个元素的List,这个元素是任意值")
      case x :: y :: Nil => println(s"$x  $y")
      case 0 :: tail => println("0 ...")
      case _ => println("something else")
    }*/

    /*val tup = (2, 3, 7)
    tup match {
      case (1, x, y) => println(s"1, $x , $y")
      case (_, z, 5) => println(z)
      case  _ => println("else")
    }*/

    //样例类
    /*val arr = Array(People("chenderong",18),Time(2333),CheckOut)
    val result: Product with Serializable = arr(Random.nextInt(arr.length))
    result match {
      case People(name,age) =>println("我匹配上了德荣")
      case Time(time) =>println("时间")
      case CheckOut => println("else")
    }*/


    //匹配Option对象
    val map = Map("a"->1,"b"->2)
    map.get("a1") match {
      case Some(i) =>println(i)
      case None =>println("没有对应的value")
    }
    map.getOrElse("b",0)

  }
}