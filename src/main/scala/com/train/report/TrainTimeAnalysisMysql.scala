package com.train.report

import com.train.utils.MakeATPKPI
import org.apache.spark.sql.{DataFrame, SparkSession}
import scalikejdbc.{DB, SQL}
import scalikejdbc.config.DBs


//使用muysql存储中间字典表列车出厂时间进行分析
object TrainTimeAnalysisMysql {
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
    //处理数据
    DBs.setup()
    sourceFrame.map(row =>{
      val trainId: String = row.getAs[String]("MPacketHead_TrainID")
      //调用atp报警数量统计的公共方法
      val listError: List[Int] = MakeATPKPI.makeATPErrorKPI(row)
      val listTrainTime: List[String] = DB.readOnly { implicit session =>
        SQL("select * from train0706 where trainId = ?")
          .bind(trainId)
          .map(wrs => wrs.string("trainTime"))
          .list()
          .apply()
      }
      //封装成trainTime 和  listError 的元组形式   (归根结底是wordcount)
      (listTrainTime(0),listError)
    }).rdd.reduceByKey{
      (list1,list2) => list1 zip list2 map(tp => tp._1 + tp._2)
    }.foreach(println)


    session.stop()
  }

}
