package com.scalademo

import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.{OutputMode, ProcessingTime}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object WordCount {
  def main(args: Array[String]): Unit = {
    //创建程序入口
    val spark: SparkSession = SparkSession.builder().appName("wc").master("local[*]").getOrCreate()
    //调用sparkContext
    val sc: SparkContext = spark.sparkContext
    //设置日志级别
    sc.setLogLevel("WARN")
    //导包
    import spark.implicits._
    //接收数据
    val fileDF: DataFrame = spark.readStream
      .format("socket")
      .option("host", "node01")
      .option("port", 9999)
      .load()
    //因为dataFrame确少类型安全检查，所以不能切分，将其转换为DataSet
    val fileDS: Dataset[String] = fileDF.as[String]
    //按照指定的分隔符进行切分
    val spliFile: Dataset[String] = fileDS.flatMap(_.split(" "))
    //进行统计单词出现的次数
    //val wordAndCount: Dataset[Row] = spliFile.groupBy("value").count().sort($"count".desc)
    //打印输出
    //尽可能快的输出
    /*wordAndCount.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      //.trigger(ProcessingTime(0))
      .start()
      .awaitTermination()*/
    //指定时间间隔进行输出
    /*wordAndCount.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .trigger(ProcessingTime("10 seconds"))
      .start()
      .awaitTermination()*/
    //将结果数据保存到指定地点
    spliFile.writeStream
      .format("csv")
      .option("path","E:\\data\\csv")
      .option("checkpointLocation","./999")
      .outputMode(OutputMode.Append())
      .trigger(ProcessingTime(0))
      .start()
      .awaitTermination()
  }

}
