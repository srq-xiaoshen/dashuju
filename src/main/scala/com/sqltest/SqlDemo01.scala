package com.sqltest

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}

case class Ad(t1id: String, t2id: String, t1ts: String, t2ts: String)

object SqlDemo01 {
  def main(args: Array[String]): Unit = {
    //构建环境
    val session: SparkSession = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sparkContext: SparkContext = session.sparkContext
    //日志打印级别
    sparkContext.setLogLevel("WARN")
    //导入隐式转换
    import session.implicits._
    //加载数据源（json）
    val dataFrame: DataFrame = session.read.json("D:\\tmp\\adTest003.json\\result.json")
    //解析字段
    val dataSet: Dataset[(Long, String, String, Long, Long, Long)] = dataFrame.map(x => {
      val appId: Long = x.getAs("appId")
      val ts: Long = x.getAs("createTime")
      val adslotId0: String = x.getAs("adslotId")
      val accIds0: String = x.getAs("accIds")
      val status0: Long = x.getAs("resultStatus")
      val resultTrackStatus0: Long = x.getAs("resultTrackStatus")
      val adslotId = if (adslotId0 != null) {
        adslotId0
      } else {
        "0"
      }
      val accIds = if (accIds0 != null) {
        accIds0
      } else {
        "0"
      }
      val status = if (status0 != null) {
        status0
      } else {
        1
      }
      val resultTrackStatus = if (resultTrackStatus0 != null) {
        resultTrackStatus0
      } else {
        0
      }
      (appId, adslotId, accIds, status, resultTrackStatus, ts)
    }).cache()//保存一下到内存，提高复用

    //表字段命名
    val frame1: DataFrame = dataSet.toDF("appId", "adslotId", "accIds", "status", "resultTrackStatus", "ts")
    //注册表名
    frame1.createTempView("table1")
    val frame2: DataFrame = dataSet.toDF("appId", "adslotId", "accIds", "status", "resultTrackStatus", "ts")
    frame2.createTempView("table2")
    //执行sql操作
    val result: DataFrame = session.sql(
      """
        |select t1.appId t1id,t2.appId t2id,t1.ts t1ts,t2.ts t2ts
        |  from table1 t1 left join table2 t2
        |    on t1.appId=t2.appId
        |    and t1.adslotId=t2.adslotId
        |    and t1.accIds=t2.accIds
        |    and t1.status=t2.status
        |    and t1.resultTrackStatus=t2.resultTrackStatus
        |    and t1.ts=t2.ts
        |""".stripMargin)
    //    result.show(10)
    //写出结果
    result.coalesce(1).write.json("D:\\tmp\\adTest002.json")
    session.stop()

  }
}
