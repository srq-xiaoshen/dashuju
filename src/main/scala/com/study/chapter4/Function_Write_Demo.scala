package com.study.chapter4

object Function_Write_Demo {
  def main(args: Array[String]): Unit = {
    /*val arr = Array("a b","c d")
    //map
    val result = arr.map(_.split(" ")).flatten
    //打印输出
    result.foreach(println)
    //flatMap
    arr.flatMap(_.split(" ")).foreach(println)*/


    //zip
    val arr1 = Array("lvbu","zhaoyun","dianwei")
    val arr2 =Array(12,13,15,17,19)
    /*val result: Array[(String, Int)] = arr1.zip(arr2)
    //打印输出
    println(result.toBuffer)*/

    //zipAll
    /*val result1: Array[(String, Int)] = arr1.zipAll(arr2,"kapu",99)
    //打印输出
    println(result1.toBuffer)

    //zipWithIndex
    val result2: Array[(String, Int)] = arr1.zipWithIndex
    //打印输出
    println(result2.toBuffer)*/

    //fold
    /*val arr = Array(1,2,3,4,5)
    val result: Int = arr.fold(10)(_+_)
    println(result)*/

    /*val arr = Array(1,2,3,4,5)
    //reduce
    val reuslt: Int = arr.reduce(_+_)
    println(reuslt)
    val result1: Int = arr.reduceLeft(_-_)
    println(result1)
    val result2: Int = arr.reduceRight(_-_)
    println(result2)*/

    /*val fun=(x:Int)=>{x+1}
    val lst = List(1,2,3,4,5)
    //foreach
    val a: Unit = lst.foreach(fun)
    val b: List[Int] = lst.map(fun)
    println(a.getClass)
    println(b.getClass)*/


    /*val lst = List(1,2,3,4,5,6,7,8)
    //需求：过滤出来偶数，并将偶数扩大10倍
    val result: List[Int] = lst.filter(_%2==0).map(_*10)
    println(result)
    //yield
    val result1: List[AnyVal] = for (i<-lst;if (i%2==0)) yield  i*10
    println(result1)*/

    val arr = Array("xiaohong","xiaohe","axiang","ahua")
    val result: Array[String] = arr.filter(_.startsWith("ax"))
    val result1: Array[String] = arr.filter(_.endsWith("g"))
    val result2: Array[String] = arr.filter(_.contains("ang"))
    println(result.toBuffer)
    println(result1.toBuffer)
    println(result2.toBuffer)






  }

}
