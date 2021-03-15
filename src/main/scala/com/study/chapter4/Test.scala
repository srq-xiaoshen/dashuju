package com.study.chapter4

object Test {
  def main(args: Array[String]): Unit = {
    //创建递归方法：自己调用自己本身
    //要求：必须加返回值类型
    def fact(i:Int):Int={
      if (i<=1){
        1
      }else {
        i*fact(i-1)
      }
    }
    for (i<- 1 to 10){
      println(i+"的阶乘为："+fact(i))
    }
  }

}
