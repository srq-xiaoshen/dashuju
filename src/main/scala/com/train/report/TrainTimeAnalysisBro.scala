package com.train.report

import com.train.utils.MakeATPKPI
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}



//使用广播变量的形式来接收列车出厂时间的数据
object TrainTimeAnalysisBro {
  def main(args: Array[String]): Unit = {

    //session
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import session.implicits._
    //读取数据
    val inputMainPath : String ="D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl"
    val sourceFrame: DataFrame = session.read.json(inputMainPath)
    //读取列车出厂时间的数据
    val inputTimePath : String = "D:\\work\\project\\高铁项目 - 成都0706\\day06\\列车出厂时间数据.txt"
    val trainTimeSource: RDD[String] = session.sparkContext.textFile(inputTimePath)
    //列车出厂时间数据预处理
    val trainTimeFilted: RDD[Array[String]] = trainTimeSource.map(_.split("[|]" ,-1)).filter(_.length >=2 )
    //收集到主节点上,然后广播出去 .collect.toMap 等同于 collectAsMAp
    val trainTimeMap: collection.Map[String, String] = trainTimeFilted.map(arr => (arr(0),arr(1))).collectAsMap()
    val trainTimeBro: Broadcast[collection.Map[String, String]] = session.sparkContext.broadcast(trainTimeMap)

    val result: RDD[(String, List[Int])] = sourceFrame.map(row => {
      //获取列车的车号
      val MPacketHead_TrainID: String = row.getAs[String]("MPacketHead_TrainID")
      //读取广播变量中的数据来获取列车出厂时间
      //(trainId,trainTime)  Map
      val trainTime: String = trainTimeBro.value.getOrElse(MPacketHead_TrainID, MPacketHead_TrainID)
      val listError: List[Int] = MakeATPKPI.makeATPErrorKPI(row)
      (trainTime, listError)
    }).rdd.reduceByKey {
      //(201511,List(1, 0, 0, 0, 0, 0, 0, 0, 0, 0))
      //(201511,List(1, 0, 0, 0, 0, 0, 0, 0, 0, 0))

      //进行拉链操作 (List((1,1),(0,0),(0,0),(0,0),(0,0),(0,0),(0,0),(0,0),(0,0),(0,0))
      //元组对位相加
      (list1, list2) => list1 zip list2 map (tp => tp._1 + tp._2)
    }
    result.foreach(println)

    session.stop()





  }

}
