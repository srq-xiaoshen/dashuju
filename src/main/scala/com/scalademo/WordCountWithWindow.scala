package com.scalademo

import java.sql.Timestamp

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.streaming.OutputMode

object WordCountWithWindow {
  def main(args: Array[String]): Unit = {
    //创建程序入口
    val spark: SparkSession = SparkSession.builder().appName("demo").master("local[*]").getOrCreate()
    //调用SparkContext
    val sc: SparkContext = spark.sparkContext
    //设置日志级别
    sc.setLogLevel("WARN")
    //导包
    import spark.implicits._
    //接收数据
    val df: DataFrame = spark.readStream
      .format("socket")
      .option("host", "node01")
      .option("port", 9999)
      .option("includeTimestamp", true)
      .load()
    //将DataFrame转换为DataSet
    val ds: Dataset[(String, Timestamp)] = df.as[(String,Timestamp)]
    //切分数据，并给每个单词加时间戳
    val wordDF: DataFrame = ds.flatMap(line => {
      line._1.split(" ").map(word => (word, line._2))
    }).toDF("word", "timestamp")
    //导包
    import org.apache.spark.sql.functions._
    //加窗口大小和滑动间隔
    val result: Dataset[Row] = wordDF
      .withWatermark("timestamp","10 seconds")
      .groupBy(window($"timestamp","10 seconds","5 seconds"),$"word")
      .count().sort($"count".desc)
    //打印输出
    result.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .option("truncate", "false")
      .start()
      .awaitTermination()
  }

}
