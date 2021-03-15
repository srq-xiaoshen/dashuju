package com.train.tags

import java.text.SimpleDateFormat

import com.train.utils.MakeWeather
import org.apache.commons.lang.StringUtils
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}


/*
*  atp的用户画像(生产作业中个数合和时长的标签是分开处理的)
* 1.个数标签
* 2.时长标签
* 3.合并
 */
object ATPTags {
  def main(args: Array[String]): Unit = {
    //session
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import session.implicits._
    //读取数据
    val inputMainPath: String = "D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl";
    val sourceFrame: DataFrame = session.read.json(inputMainPath).cache()
    //读取列车出厂时间数据进行数据预处理
    val inputTimePath: String = "D:\\work\\project\\高铁项目 - 成都0706\\高铁项目\\列车出厂时间数据.txt"
    val trainTimeSource: RDD[String] = session.sparkContext.textFile(inputTimePath)
    val trainTimeFilted: RDD[Array[String]] = trainTimeSource.map(_.split("[|]", -1)).filter(_.length >= 2)
    //收集上来转换成map
    val trainTimeMap: collection.Map[String, String] = trainTimeFilted.map(arr => (arr(0), arr(1))).collectAsMap()
    //将列车出厂时间数据广播
    val trainTimeBro: Broadcast[collection.Map[String, String]] = session.sparkContext.broadcast(trainTimeMap)
    //读取列车生产厂家数据
    val inputFactoryPath: String = "D:\\work\\project\\高铁项目 - 成都0706\\高铁项目\\列车生产厂家.txt"
    val trainFactorySource: RDD[String] = session.sparkContext.textFile(inputFactoryPath)
    //进行数据预处理
    val trainFactoryFilted: RDD[Array[String]] = trainFactorySource.map(_.split("[|]", -1)).filter(_.length >= 2)
    //收集上来转换成map
    val trainFactoryMap: collection.Map[String, String] = trainFactoryFilted.map(arr => (arr(0), arr(1))).collectAsMap()
    //将生产厂家数据广播
    val trainFactoryBro: Broadcast[collection.Map[String, String]] = session.sparkContext.broadcast(trainFactoryMap)
    //读取ATP检修台账数据
    val inputCheckPath: String = "D:\\work\\project\\高铁项目 - 成都0706\\高铁项目\\ATP检修台账.txt"
    val trainCheckSource: RDD[String] = session.sparkContext.textFile(inputCheckPath)
    //进行数据的预处理
    val trainCheckFilted: RDD[Array[String]] = trainCheckSource.map(_.split("[|]", -1)).filter(_.length >= 4)
    //收集上来转换成map
    val trainCheckMap: collection.Map[String, (String, String, String)] = trainCheckFilted.map(arr => (arr(0), (arr(1), arr(2), arr(3)))).collectAsMap()
    //将ATP检修台账数据广播
    val trainCheckBro: Broadcast[collection.Map[String, (String, String, String)]] = session.sparkContext.broadcast(trainCheckMap)

    //个数标签
    //val countTags: RDD[(String, List[(String, Long)])] =
      sourceFrame.map(row => {
      //获取ATP的唯一标识,也就是车号
      val trainId: String = row.getAs[String]("MPacketHead_TrainID")
      //创建一个容器去存放标签
      var list = List[(String, Long)]()
      //报警部位
      //获取atperror
      val atperror: String = row.getAs[String]("MATPBaseInfo_AtpError")
      //判断atperror是否为空,如果不为空才记录标签
      if (StringUtils.isNotEmpty(atperror)) {
        //获取湿度,温度,天气,速度
        val humLevel: String = MakeWeather.getHum(row)
        val temLevel: String = MakeWeather.getTem(row)
        val weaLevel: String = MakeWeather.getWea(row)
        val speLevel: String = MakeWeather.getSpe(row)
        list :+= ("PA" + humLevel + "/" + temLevel + "/" + weaLevel + "/" + speLevel + ":" + atperror, 1L)
      }
      //出厂时间标签
      //获取列车的trainId,获取列车出厂时间
      val trainTime: String = trainTimeBro.value.getOrElse(trainId, trainId)
      list :+= ("TI" + trainTime, 1L)
      //atp类型标签
      val atpType: String = row.getAs[String]("MPacketHead_ATPType")
      if (StringUtils.isNotEmpty(atpType)) {
        list :+= ("TY" + atpType, 1L)
      }
      //司机标签
      val diverId: String = row.getAs[String]("MPacketHead_DriverID")
      if (StringUtils.isNotEmpty(atpType)) {
        list :+= ("DR" + diverId, 1L)
      }

      //生产厂家标签:首先需要获取trainId,然后截取第一位,去列车生产厂家字典表中查找列车生产厂家数据
      val trainFactory: String = trainFactoryBro.value.getOrElse(trainId.substring(0, 1), trainId)
      list :+= ("FA" + trainFactory, 1L)

      //检修和更换部位标签: 拿出trainId,去检修台账中查找是检修还是更换
      val trainCheck: (String, String, String) = trainCheckBro.value.getOrElse(trainId, ("", "", ""))
      if (trainCheck._1.equals("检修")) {
        list :+= ("AJX" + trainCheck._2 + trainCheck._3, 1L)
      } else if (trainCheck._1.equals("更换")) {
        list :+= ("AGH" + trainCheck._2 + trainCheck._3, 1L)
      }
      (trainId, list)
    }).rdd.reduceByKey {
      (list1, list2) => (list1 ++ list2).groupBy(_._1).mapValues(li => li.foldLeft(0L)(_ + _._2)).toList
    }

    //时长的标签统计
    //val timeTags: RDD[(String, List[(String, Long)])] =

      sourceFrame.filter(
      """
        |
        |Humidity != '' and
        |Temperature != '' and
        |Weather != '' and
        |MATPBaseInfo_Speed != '' and
        |MATPBaseInfo_DataTime != ''
        |
        |""".stripMargin
    ).map(row => {
      //获取列车车号
      val trainId: String = row.getAs[String]("MPacketHead_TrainID")
      //创建一个容器去存放时间标签
      var list = List[(String, Long)]()
      //获取湿度,温度,天气,速度
      val humLevel: String = MakeWeather.getHum(row)
      val temLevel: String = MakeWeather.getTem(row)
      val weaLevel: String = MakeWeather.getWea(row)
      val speLevel: String = MakeWeather.getSpe(row)
      //获取时间点
      val dataTime: String = row.getAs[String]("MATPBaseInfo_DataTime")
      //定义时间戳类型
      val format: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
      val time: Long = format.parse(dataTime).getTime
      list :+= (humLevel, time)
      list :+= (temLevel, time)
      list :+= (weaLevel, time)
      list :+= (speLevel, time)
      (trainId, list)
    }).rdd.reduceByKey {
      (list1, list2) => list1 ++ list2
    }.map(tp => {
      val list: List[(String, Long)] =
        tp._2.sliding(5, 1).map(li => (li.head._1, li.last._2 - li.head._2)).toList
        .groupBy(_._1).mapValues(li => li.map(_._2).sum).toList
      (tp._1, list)
    })



    //实际生产中分开放置  现在联合起来是为了测试用
   /* timeTags.union(countTags).reduceByKey{
      (list1,list2) => list1 ++ list2
    }*/

    session.stop()



  }

}
