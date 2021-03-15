package com.utils

object TranType {
  //字符串转换int
  def toInt(str:String)={
    try {
      str.toInt
    } catch {
      case _:Exception => 0
    }
  }
  //字符串转换double
  def toDouble(str : String) = {
    try {
      str.toDouble
    } catch {
      //发生异常的情况下,默认输出0.0 代码安全保护  代码健壮性提升
      case _: Exception => 0.0
    }
  }

}
