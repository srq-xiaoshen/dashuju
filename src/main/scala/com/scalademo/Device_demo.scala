package com.scalademo

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}

object Device_demo {
  def main(args: Array[String]): Unit = {
    //创建sparkSql程序入口
    val spark: SparkSession = SparkSession.builder().appName("demo").master("local[*]").getOrCreate()
    //调用sparkContext
    val sc: SparkContext = spark.sparkContext
    //设置日志级别
    sc.setLogLevel("WARN")
    //导包
    import spark.implicits._
    //加载数据
    val df: DataFrame = spark.read.json("E:\\demo.json")
    //{"device":"Michael","deviceType": "people","signal": 15,"time": "2018-01-02 15:20:00"}
    //1.信号强度大于10的设备
    df.filter($"signal">10).show()
    //2.各种设备类型的数量
    df.groupBy("deviceType").count().sort($"count".desc).show()
    //3.各种设备类型的平均信号强度
    df.groupBy("deviceType").avg("signal").show()
  }

}
