package com.scalademo

import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.{OutputMode, ProcessingTime}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object StructuredStreaming_Kafka_Demo {
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
    val wordsDF: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "node01:9092,node02:9092,node03:9092")
      .option("subscribe", "test")
      .load()
    //获取kafka当中的value
    val file: Dataset[String] = wordsDF.selectExpr("CAST(value AS STRING)").as[String]
    //进行切分
    val spliFile: Dataset[String] = file.flatMap(_.split(" "))
    //统计单词个数
    val result: Dataset[Row] = spliFile.groupBy("value").count().sort($"count".desc)
    //打印输出
    result.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .trigger(ProcessingTime(0))
      .start()
      .awaitTermination()
  }

}
