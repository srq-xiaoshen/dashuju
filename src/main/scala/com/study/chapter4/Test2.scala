package com.study.chapter4

object Test2 {
  def main(args: Array[String]): Unit = {
    def factory(i:Int):Int={
      def fact(i:Int,accumulator:Int):Int={
        if (i<=1){
          accumulator
        }else{
          fact(i-1,i*accumulator)
        }
      }
      fact(i,1)
    }

    println(factory(0)) //1
    println(factory(1)) //1
    println(factory(2)) //2
    println(factory(3)) //6
  }

}
