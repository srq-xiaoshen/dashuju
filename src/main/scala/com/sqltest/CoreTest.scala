package com.sqltest

import kafka.network.RequestChannel.Session
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object CoreTest {
  def main(args: Array[String]): Unit = {

    //构建执行环境（配置）
    val session: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName(this.getClass.getName)
      .getOrCreate()
    val sc: SparkContext = session.sparkContext
    //加载文件
    import session.implicits._
  val dataFrame: DataFrame = session.read.text("D:\\tmp\\数据.txt")

  }
}
