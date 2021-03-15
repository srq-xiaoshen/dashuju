package com.train.tools

import java.util.Properties

import com.train.config.ConfigHelper
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SaveMode, SparkSession}


//将出厂时间数据写入mysql
object TrainTime2Mysql {
  def main(args: Array[String]): Unit = {
    //session
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import session.implicits._
    //读入出厂时间数据
    val inputPath : String = "D:\\work\\project\\高铁项目 - 成都0706\\day06\\列车出厂时间数据.txt"
    val sourceRDD: RDD[String] = session.sparkContext.textFile(inputPath)
    //切割并过滤
    val filtedRDD: RDD[Array[String]] = sourceRDD.map(_.split("[|]", -1)).filter(_.length >=2)
    //创建properties
    val properties: Properties = new Properties()
    properties.setProperty("diver",ConfigHelper.driver)
    properties.setProperty("user",ConfigHelper.user)
    properties.setProperty("password",ConfigHelper.password)
    //输入存入mysql
    filtedRDD.map(arr =>((arr(0),arr(1))))
      .toDF("trainId","trainTime")
      .write.mode(SaveMode.Overwrite).jdbc(ConfigHelper.url,"train0706",properties)

    //释放资源
    session.stop()


  }

}
