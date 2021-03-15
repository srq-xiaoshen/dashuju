package com.train.utils

import com.train.config.ConfigHelper
import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.OffsetRange
import scalikejdbc.{DB, SQL}
import scalikejdbc.config.DBs


//维护偏移量
object ManagerOffset {
    //将偏移量维护到mysql中
    def saveOffset2Mysql(offsetRange : Array[OffsetRange]) ={
      //scalikejdbc加载配置
      DBs.setup()
      DB.localTx{ implicit session =>
        offsetRange.foreach(ore =>{
          //SQL更新mysql中的偏移量数据
          SQL("update trainOffset set offset = ? where topic = ? and groupid =? and partition = ?")
            .bind(ore.untilOffset,ore.topic,ConfigHelper.groupId,ore.partition)
            .update()
            .apply()
        })
      }
    }

      //从mysql中读取偏移量
  def getOffsetFromMysql() ={
    DBs.setup()
    DB.readOnly{implicit  session =>
      SQL("select * from trainOffset where topic = ? and groupid = ?")
        .bind(ConfigHelper.topics(0),ConfigHelper.groupId)
        .map(wrs =>(
          //解析出结果中的topic 封装成TopicPartition,和offset组成元组
        new TopicPartition(wrs.string("topic"),wrs.int("partition")),
          wrs.long("offset")
        ))
        .list()
        .apply()
    }.toMap
  }

}
