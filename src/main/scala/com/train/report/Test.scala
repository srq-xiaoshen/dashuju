package com.train.report

import org.apache.commons.configuration.PropertiesConfiguration

object Test {
  def main(args: Array[String]): Unit = {
    val path = "D:\\develop\\workspace\\train0706\\src\\main\\resources\\test.prop"
    val configuration: PropertiesConfiguration = new PropertiesConfiguration(path)
    val str: String = configuration.getString("inputMainPath")
    println(new String(str.getBytes("iso-8859-1"),"UTF-8"))
  }

}
