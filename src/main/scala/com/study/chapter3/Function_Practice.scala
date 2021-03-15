package com.study.chapter3

object Function_Practice {
  def main(args: Array[String]): Unit = {
    //map和flatMap
    /*val arr=Array(("A",1),("B",2),("C",3))
    //arr.map(x=>x._1+x._2).foreach(println)
    arr.flatMap(x=>x._1+x._2).foreach(println)*/
    /*val arr=Array("a b","c d")
    val result = arr.map(x=>x.split(" ")).flatten
    println(result.toBuffer)
    //flatMap
    val results: Array[String] = arr.flatMap(x=>x.split(" "))
    println(results.toBuffer)*/


    //fold家族
    /*val lst = List(1,2,3,4,5,0)
    //fold:必须指定初始值，初始值参与运算
    val result: Int = lst.fold(0)(_-_)
    //打印输出
    println(result)
    //foldLeft
    val result1: Int = lst.foldLeft(0)(_-_)
    //打印输出
    println(result1)
    //foldRight
    val result2: Int = lst.foldRight(0)(_-_)
    //打印输出
    println(result2)*/
    //执行流程
    //5-0=5
    //4-5=(-1)
    //3-(-1)=4
    //2-4=-2
    //1-(-2)=3

    //reduce家族
   /* val lst = List(1,2,3,4,5)
    //reduce
    val result: Int = lst.reduce(_-_)
    //打印输出
    println(result)
    val result1: Int = lst.reduceLeft(_-_)
    println(result1)
    val result2: Int = lst.reduceRight(_-_)
    println(result2)*/


    //scan家族
   /* val lst = List(1,2,3,4,5)
    val result: List[Int] = lst.scan(0)(_-_)
    //打印输出
    println(result)
    //scanLeft
    val result1: List[Int] = lst.scanLeft(0)(_-_)
    println(result1)
    //scanRight
    val result2: List[Int] = lst.scanRight(0)(_-_)
    println(result2)*/


    //迭代器
    val lst = List(1,2,3,4,5).iterator
    while (lst.hasNext){
      println(lst.next())
    }

















  }

}
