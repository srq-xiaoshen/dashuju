package com.train.utils

//数据类型转换
object TurnType {
  //字符串类型转换成double类型
  def toDouble(str : String) = {
    try {
      str.toDouble
    } catch {
      //发生异常的情况下,默认输出0.0 代码安全保护  代码健壮性提升
      case _: Exception => 0.0
    }
  }

}
