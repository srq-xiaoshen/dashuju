package com.study.chapter4

object untils{
  //指定隐式值
  implicit val a = "zhangsan"
  implicit val b = 13
}

object Implicit_Demo {
  def main(args: Array[String]): Unit = {
    //放到工具类当中，调用隐式值
    import untils._
    //创建一个方法
    def People(implicit name:String,age:Int)={
      println(name)
      println(age)
    }
    People
  }

}
