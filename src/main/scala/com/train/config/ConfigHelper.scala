package com.train.config

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.common.serialization.StringDeserializer

//获取配置的类
object ConfigHelper {
  //创建load对象
  private lazy val load: Config = ConfigFactory.load()
  //加载mysql的配置
  val driver: String = load.getString("db.default.driver")
  val url: String = load.getString("db.default.url")
  val user: String = load.getString("db.default.username")
  val password: String = load.getString("db.default.password")

  //加载主数据路径
  //val inputMainPath: String = load.getString("inputMainPath")

  //加载kafka相关参数
  val topics: Array[String] = load.getString("topics").split("[,]")
  val groupId: String = load.getString("groupid")


  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "offcn01:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> groupId,
    "auto.offset.reset" -> "earliest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )


}
