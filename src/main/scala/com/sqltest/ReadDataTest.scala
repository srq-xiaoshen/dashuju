package com.sqltest

import org.apache.spark.{SparkConf, SparkContext}

object ReadDataTest {
  def main(args: Array[String]): Unit = {

    val conf= new SparkConf().setAppName(this.getClass.getName).setMaster("local[*]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val data = sc.textFile("/opt/module/datas/data.txt")
    val result = data.map(x => x.split("[;]"))
    val rdd = result.map(x=>(x(0),x(1),x(2),x(3)))
    rdd.foreach(println)
  }
}
