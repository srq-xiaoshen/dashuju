package com.config

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.common.serialization.StringDeserializer

//获取配置的类
object ConfigHelper {
  //创建load对象
  private lazy val load: Config = ConfigFactory.load()
  //加载mysql的配置
  val driver: String = load.getString("db.default.driver")
  val url: String = load.getString("db.default.url")
  val user: String = load.getString("db.default.user")
  val password: String = load.getString("db.default.password")
  val poolSize: Int = load.getString("db.default.poolSize").toInt

  //加载kafka配置
  val topics: Array[String] = load.getString("topics").split(",")
  val groupId: String = load.getString("groupId")
  //redis配置
  val redisPasswd: String = load.getString("redisPasswd")

  //spark中kafka配置
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "hadoop006:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> groupId,
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  //redis配置


}