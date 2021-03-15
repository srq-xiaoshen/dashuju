package com.train.online

import com.train.config.ConfigHelper
import com.train.utils.{MakeATPStreamKPI, MakeRedisConn, ManagerOffset}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.Jedis
//atp实时报警统计分析
object AtpOnlineAnalysis {
  def main(args: Array[String]): Unit = {
    //配置文件上下文
    val conf: SparkConf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[*]")
      //设置序列化方式
      .set("spark.serializer","org.apche.spark.serializer.KryoSerializer")
    //设置优雅的方式关闭
      .set("spark.streaming.stopGracefullyOnShutdown","true")
    //创建数据接收器
    val ssc: StreamingContext = new StreamingContext(conf,Seconds(2))
    val dstream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferBrokers,
      ConsumerStrategies.Subscribe[String, String](
        ConfigHelper.topics,
        ConfigHelper.kafkaParams,
        //核心代码 创建kafka消费者的时候,从mysql中获取偏移量
        ManagerOffset.getOffsetFromMysql()
      )
    )
    //处理数据
    //手动维护偏移量,最好是每个rdd的每个分区维护一个偏移量
    dstream.foreachRDD(rdd =>{
      //判断rdd是否为空
      if(!rdd.isEmpty()){
        //如果rdd不为空,需要获取偏移量
        //核心代码
        val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        //如果rdd不为空执行以下操作,获取数据
        val source: RDD[String] = rdd.map(_.value()).filter(_.length != 0)
        //数据预处理
        val filted: RDD[Array[String]] = source.map(_.split("[|]",-1)).filter(_.length>=55)
        //处理数据
        val result: RDD[(String, List[Int], String, String, String)] = filted.map(arr => {
          //获取数据时间
          val dataTime: String = arr(7)
          //获取日期  20150109102323
          val day: String = dataTime.substring(0, 8)
          //获取小时
          val hour: String = dataTime.substring(0, 10)
          //获取分钟
          val minute: String = dataTime.substring(0, 12)
          //获取归属铁路局
          val MPacketHead_AttachRWBureau: String = arr(3)
          //获取报警atperror
          val atperrror: String = arr(17)
          //调用atp报警的公共方法(流式处理的方法)
          val list: List[Int] = MakeATPStreamKPI.makeATPErrorKPI(atperrror)
          (day, list, hour, minute, MPacketHead_AttachRWBureau)
        })
        //将每天的统计和写入库中
        //(day,list)
        result.map(tp =>(tp._1,tp._2))
          .reduceByKey{
            (list1,list2) => list1 zip list2 map(tp=> tp._1+tp._2)
          }.foreachPartition(partition=>{
          //获取redis
          val jedis: Jedis = MakeRedisConn.makeRedis(0)
          partition.foreach(tp =>{
            //写入redis
            //hset重新设置值
            //hincrBy   20150606dataall   120
            jedis.hincrBy("trainStreaming",tp._1 + "dataall",tp._2(0))
            jedis.hincrBy("trainStreaming",tp._1 + "atperror",tp._2(1))
            jedis.hincrBy("trainStreaming",tp._1 + "main",tp._2(2))
            jedis.hincrBy("trainStreaming",tp._1 + "wifi",tp._2(3))
            jedis.hincrBy("trainStreaming",tp._1 + "balise",tp._2(4))
            jedis.hincrBy("trainStreaming",tp._1 + "TCR",tp._2(5))
            jedis.hincrBy("trainStreaming",tp._1 + "speed",tp._2(6))
            jedis.hincrBy("trainStreaming",tp._1 + "DMI",tp._2(7))
            jedis.hincrBy("trainStreaming",tp._1 + "TIU",tp._2(8))
            jedis.hincrBy("trainStreaming",tp._1 + "JRU",tp._2(9))
          })
        })
        //将每个小时的统计和写入库中
        result.map(tp =>(tp._3,tp._2))
          .reduceByKey{
            (list1,list2) => list1 zip list2 map(tp => tp._1+ tp._2)
          }.foreachPartition(partition => {
          //获取redis
          val jedis = MakeRedisConn.makeRedis(0)
          partition.map(tp =>{
            //写入redis
            //hset重新设置值
            //hincrBy   2015060613dataall  125
            jedis.hincrBy("trainStreaming",tp._1 + "dataall",tp._2(0))
            jedis.hincrBy("trainStreaming",tp._1 + "atperror",tp._2(1))
            jedis.hincrBy("trainStreaming",tp._1 + "main",tp._2(2))
            jedis.hincrBy("trainStreaming",tp._1 + "wifi",tp._2(3))
            jedis.hincrBy("trainStreaming",tp._1 + "balise",tp._2(4))
            jedis.hincrBy("trainStreaming",tp._1 + "TCR",tp._2(5))
            jedis.hincrBy("trainStreaming",tp._1 + "speed",tp._2(6))
            jedis.hincrBy("trainStreaming",tp._1 + "DMI",tp._2(7))
            jedis.hincrBy("trainStreaming",tp._1 + "TIU",tp._2(8))
            jedis.hincrBy("trainStreaming",tp._1 + "JRU",tp._2(9))
          })
         }
        )
      }
    })
  }
}
