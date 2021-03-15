package com.train.report

import org.apache.spark.sql.{DataFrame, SparkSession}

object AttachRWBureauAnalysisSQL {
  def main(args: Array[String]): Unit = {
    //创建session
    val session: SparkSession = SparkSession.builder()
      .appName("AttachRWBureauAnalysisSQL")
      .master("local[*]")
      .getOrCreate()
    //读取数据
    val inputPath : String ="D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl"
    val sourceFrame: DataFrame = session.read.json(inputPath)
    //注册日志表
    sourceFrame.createTempView("logs")

    //注册自定义函数
    session.udf.register("my",( b : Boolean) => if (b) 1 else 0)

    //写sql语句
    //sql语句if,只能是两个分支
    //如果想用if elseif  else ,sql语句中只能使用 case when 来做
    session.sql(
      """
        |
        |select MPacketHead_AttachRWBureau, count(*) as dataall,
        |sum(if(MATPBaseInfo_AtpError != '',1,0)) as atperror,
        |sum(case when MATPBaseInfo_AtpError = '车载主机' then 1 else 0 end) as main,
        |sum(my(MATPBaseInfo_AtpError = "无线传输单元")) as wifi,
        |sum(if(MATPBaseInfo_AtpError = "应答器信息接收",1,0)) as balise,
        |sum(if(MATPBaseInfo_AtpError = "轨道电路信息读取器",1,0)) as TCR,
        |sum(if(MATPBaseInfo_AtpError = "测速测距单元",1,0)) as speed,
        |sum(if(MATPBaseInfo_AtpError = "人机交互接口",1,0)) as DMI,
        |sum(if(MATPBaseInfo_AtpError = "列车接口单元",1,0)) as TIU,
        |sum(if(MATPBaseInfo_AtpError = "司法记录单元",1,0)) as JRU
        |from logs
        |
        |group by MPacketHead_AttachRWBureau
        |
        |""".stripMargin
    ).show()

    session.stop()

  }

}
