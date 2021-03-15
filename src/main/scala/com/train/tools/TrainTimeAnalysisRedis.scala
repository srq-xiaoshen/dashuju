package com.train.tools

import com.train.utils.{MakeATPKPI, MakeRedisConn}
import org.apache.spark.sql.{DataFrame, SparkSession}
import redis.clients.jedis.Jedis


//redis来存储列车出厂时间数据进行统计分析
//redis是基于内存的数据库,避免了频繁的磁盘IO过程,从而增加了效率
object TrainTimeAnalysisRedis {
  def main(args: Array[String]): Unit = {

      //session
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import session.implicits._
    //读取数据
    val inputMainPath : String = "D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl"
    val sourceFrame: DataFrame = session.read.json(inputMainPath)
    //处理数据,从redis中获取列车出厂时间数据,然后进行统计分析
    sourceFrame.mapPartitions(partition => {
      //获取redis连接
      val jedis: Jedis = MakeRedisConn.makeRedis(0)
      val tuples: Iterator[(String, List[Int])] = partition.map(row => {
        //从主数据源(ETL后的)获取车号
        val trainId: String = row.getAs[String]("MPacketHead_TrainID")
        //从redis中获取列车出厂时间 hget
        val trainTime: String = jedis.hget("trainTime", trainId)
        val listError: List[Int] = MakeATPKPI.makeATPErrorKPI(row)
        //返回元组(列车出厂时间,指标统计(1,0,0,......))
        (trainTime, listError)
      })
      //关闭redis资源
      jedis.close()
      tuples
    }).rdd.reduceByKey{
      (list1,list2) => list1 zip list2  map(tp => tp._1 + tp._2 )
    }

    session.stop()

  }

}
