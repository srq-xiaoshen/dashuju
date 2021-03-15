package com.scalademo

import java.sql.Timestamp

import com.alibaba.fastjson.JSON
import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
case class DeviceData(device: String, deviceType: String, signal: Double, time: Timestamp)
object Device_Demo1 {
  def main(args: Array[String]): Unit = {
    //创建程序入口
    val spark: SparkSession = SparkSession.builder().appName("demo").master("local[*]").getOrCreate()
    //调用sparkContext
    val sc: SparkContext = spark.sparkContext
    //设置日志级别
    sc.setLogLevel("WARN")
    //导包
    import spark.implicits._
    //加载数据
    val df: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "node01:9092,node02:9092,node03:9092")
      .option("subscribe", "test")
      .load()
    //将字节码数组转换为value
    val ds: Dataset[String] = df.selectExpr("CAST(value AS STRING)").as[String]
    //获取字节码对象
    val deviceDataDS: Dataset[DeviceData] = ds.map(line=>JSON.parseObject(line,classOf[DeviceData]))
    ////各种设备类型的数量
    val result: Dataset[(String, Long)] = deviceDataDS.groupByKey(_.deviceType).count()
    //打印输出
    result.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .start()
      .awaitTermination()
  }

}
