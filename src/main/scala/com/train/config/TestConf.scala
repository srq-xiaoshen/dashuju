package com.train.config

import org.apache.commons.configuration.PropertiesConfiguration

object TestConf {
  def main(args: Array[String]): Unit = {
    val configuration: PropertiesConfiguration = new PropertiesConfiguration("D:\\develop\\workspace\\train0706\\src\\main\\resources\\test.conf")
    val str: String = configuration.getString("inputMainPath")
  }

  def getMainPath(): String ={
    val configuration: PropertiesConfiguration = new PropertiesConfiguration("D:\\develop\\workspace\\train0706\\src\\main\\resources\\test.conf")
    val path: String = configuration.getString("inputMainPath")
    path
  }
}
