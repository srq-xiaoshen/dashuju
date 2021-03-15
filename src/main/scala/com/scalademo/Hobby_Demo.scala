package com.scalademo

import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.{OutputMode, ProcessingTime}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.types.StructType

object Hobby_Demo {
  def main(args: Array[String]): Unit = {
    //创建程序入口
    val spark: SparkSession = SparkSession.builder().appName("demo").master("local[*]").getOrCreate()
    //调用sparkContext
    val sc: SparkContext = spark.sparkContext
    //设置日志级别
    sc.setLogLevel("WARN")
    //导包
    import spark.implicits._
    //指定schema约束信息
    val schema: StructType = new StructType()
      .add("name", "string")
      .add("age", "int")
      .add("hobby", "string")
    //加载监听文件夹目录
    val fileDF: DataFrame = spark.readStream.schema(schema).json("E:\\data")
    //统计年龄小于25岁爱好排行榜
    val result: Dataset[Row] = fileDF.filter($"age"<25).groupBy("hobby").count().sort($"count".desc)
    //打印输出
    result.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .trigger(ProcessingTime(0))
      .start()
      .awaitTermination()
  }

}
