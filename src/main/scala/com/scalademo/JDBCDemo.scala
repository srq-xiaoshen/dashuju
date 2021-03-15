package com.scalademo

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}

object JDBCDemo {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("aa").master("local[*]").getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")

    val data: DataFrame = spark.read.json("D:\\study\\demo.json")
    data.show()
    data.write.format("jdbc")
      .option("url","jdbc:mysql://localhost:3306/srq")
      .option("user","root")
      .option("password","123")
      .option("dbtable","jsontest")//写入数据库时不需要创建表，会自动创建
      .save()
  }
}
