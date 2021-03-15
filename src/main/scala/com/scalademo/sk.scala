package com.scalademo

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
object sk {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
    val sc: SparkContext = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val ssc = new StreamingContext(sc, Seconds(5))

/*    val dstream: InputDStream[String] = ssc.socketTextStream("company",9999)
    val data1: DStream[String] = dstream.flatMap(_.split(" "))
    val result: DStream[(String, Int)] = data1.map((_,1)).reduceByKey(_+_)
    result.count()
    result.print()*/
val kafkaParams = Map[String, Object](
  "bootstrap.servers" -> "company:9092",//用于初始化链接到集群的地址
  "key.deserializer" -> classOf[StringDeserializer],//key反序列化
  "value.deserializer" -> classOf[StringDeserializer],//value反序列化
  "group.id" -> "group3",//用于标识这个消费者属于哪个消费团体
  "auto.offset.reset" -> "latest",//偏移量 latest自动重置偏移量为最新的偏移量
  "enable.auto.commit" -> (false: java.lang.Boolean)//如果是true，则这个消费者的偏移量会在后台自动提交
)
    //kafka 设置kafka读取topic
    val topics = Array("srqqq")

    val dStreaming = KafkaUtils.createDirectStream(ssc,
      LocationStrategies.PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    dStreaming.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
