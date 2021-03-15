package com.scalademo

import scala.util.Random

case class SubmitTask(id:String,name: String)
case class HeartBeat(time:Long)
case object CheckTimeOutTask
object Demo01 {
  def main(args: Array[String]): Unit = {
    val arr: Array[Product] = Array(CheckTimeOutTask,SubmitTask("1023","lisi"),HeartBeat(3657345))
    arr(Random.nextInt(arr.length)) match {
      case SubmitTask(id,name)=>{
        println(id+name)
      }
      case HeartBeat(time)=>{
        println(time)
      }
      case CheckTimeOutTask=>{
        println("checkouttask")
      }
    }
  }

}