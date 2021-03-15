package com.train.report

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object TrainTimeAnalysisSQL {
  def main(args: Array[String]): Unit = {
    //session
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import session.implicits._
    //读取主数据
    val inputPath : String =  "D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl"
    val sourceFrame: DataFrame = session.read.json(inputPath)
    //使用主数据注册数据表
    sourceFrame.createTempView("logs")
    //读取列车出厂时间
    val aa = "D:/work/project/高铁项目 - 成都0706/day06/列车出厂时间数据.txt"
    val trainTimePath : String = aa
    val trainTimesource: RDD[String] = session.sparkContext.textFile(trainTimePath)
    //数据预处理
    val trainTimeFilted: RDD[Array[String]] = trainTimesource.map(_.split("[|]" ,-1)).filter(_.length >=2)
    val trainTimeDF: DataFrame = trainTimeFilted.map(arr => (arr(0),arr(1))).toDF("trainId","trainTime")
    //注册出厂时间表
    trainTimeDF.createTempView("trainTime")
    //执行SQL语句查询
    session.sql(
      """
        |
        |select trainTime,sum(b.dataall),sum(b.atperror),sum(b.main),
        |sum(b.wifi),sum(b.balise),sum(b.TCR),sum(b.speed),sum(b.DMI),
        |sum(b.TIU),sum(b.JRU) from trainTime a join
        |(
        |select MPacketHead_TrainID, count(*) as dataall,
        |sum(if(MATPBaseInfo_AtpError != '',1,0)) as atperror,
        |sum(case when MATPBaseInfo_AtpError = '车载主机' then 1 else 0 end) as main,
        |sum(if(MATPBaseInfo_AtpError = "无线传输单元",1,0)) as wifi,
        |sum(if(MATPBaseInfo_AtpError = "应答器信息接收",1,0)) as balise,
        |sum(if(MATPBaseInfo_AtpError = "轨道电路信息读取器",1,0)) as TCR,
        |sum(if(MATPBaseInfo_AtpError = "测速测距单元",1,0)) as speed,
        |sum(if(MATPBaseInfo_AtpError = "人机交互接口",1,0)) as DMI,
        |sum(if(MATPBaseInfo_AtpError = "列车接口单元",1,0)) as TIU,
        |sum(if(MATPBaseInfo_AtpError = "司法记录单元",1,0)) as JRU
        |from logs
        |group by MPacketHead_TrainID
        |) b
        |on a.trainId = b.MPacketHead_TrainID
        |group by trainTime
        |
        |""".stripMargin
    ).show()
    session.stop()

  }

}
