package com.train.report

import java.util.Properties

import com.google.gson.Gson
import com.train.utils.MakeATPKPI
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import scalikejdbc.{DB, SQL}
import scalikejdbc.config.DBs


//使用sparkcore实现ATP的归属铁路局分析
object AttachRWBureauAnalysis {
  def main(args: Array[String]): Unit = {

    //创建sparksession
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import session.implicits._
    //读取数据
    val inputPath: String = "D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl"
    val sourceFrame: DataFrame = session.read.json(inputPath)
    val result: RDD[(String, List[Int])] = sourceFrame.map(row => {
      //获取去配属铁路局
      val MPacketHead_AttachRWBureau: String = row.getAs[String]("MPacketHead_AttachRWBureau")
      val listError: List[Int] = MakeATPKPI.makeATPErrorKPI(row)
      (MPacketHead_AttachRWBureau, listError)
    }).rdd.reduceByKey {
      //(京,List(1, 1, 0, 1, 0, 0, 0, 0, 0, 0))
      //(京,List(1, 0, 0, 0, 0, 0, 0, 0, 0, 0))

      //每条数据的list做拉链
      //(京,List((1,1),(1,0),(0,0),(1,0),(0,0),(0,0),(0,0),(0,0),(0,0),(0,0))
      //拉链后元组中的数据相加    (套用wordcount)

      (list1, list2) => list1 zip list2 map (tp => tp._1 + tp._2)
    }

    //打印数据进行验证
    //result.foreach(println)

    val outputPath: String = "D:\\work\\project\\高铁项目 - 成都0706\\day06\\output"

    //写成json  方法1 使用dataframe
    /* result.map(tp =>
       (tp._1,tp._2(0),tp._2(1),tp._2(2),tp._2(3),tp._2(4),tp._2(5),tp._2(6),tp._2(7),tp._2(8),tp._2(9)))
       .toDF("AttachRWBureau","alldata","atperror","main","wifi","balise","TCR","speed","DMI","TIU","JRU")
       .write.json(outputPath)*/

    //写成json 方法2 使用 google gson来做
    /*result.map(tp => {
      //创建gson对象
      val gson: Gson = new Gson()
      gson.toJson(
        AttachRWBureau(tp._1,tp._2(0),tp._2(1),tp._2(2),tp._2(3),tp._2(4),tp._2(5),tp._2(6),tp._2(7),tp._2(8),tp._2(9)))})
*/

    /*//写入mysql中, 方法1 使用dataframe ,要求不能创建表,如果创建表了,可以使用mode API来协调
    val properties: Properties = new Properties()
    properties.setProperty("driver",ConfigHelper.driver)
    properties.setProperty("user",ConfigHelper.user)
    properties.setProperty("password",ConfigHelper.password)

    //数据写入mysql的过程
    result.map(tp =>(tp._1,tp._2(0),tp._2(1),tp._2(2),tp._2(3),tp._2(4),tp._2(5),tp._2(6),tp._2(7),tp._2(8),tp._2(9)))
      .toDF("AttachRWBureau","alldata","atperror","main","wifi","balise","TCR","speed","DMI","TIU","JRU")
      .write.mode(SaveMode.Overwrite).jdbc(ConfigHelper.url,"AttachRWBureau0706",properties)*/


    //写入mysql中,方法2 使用scalikejdbc,要求是表已经创建好了
    //使用scalikejdbc,最好加上事务,一个分区一个事务


    //加载配置文件
    DBs.setup()
    result.foreachPartition(partition =>{
      //一个分区一个事务
      DB.localTx{ implicit  session =>
        //该分区内,每一天数据进行插入
        partition.foreach(tp => {
          //写SQL语句
          SQL("insert into AttachRWBureau0706 values (?,?,?,?,?,?,?,?,?,?,?)")
            //绑定参数
            .bind(tp._1,tp._2(0),tp._2(1),tp._2(2),tp._2(3),tp._2(4),tp._2(5),tp._2(6),tp._2(7),tp._2(8),tp._2(9))
            //表数据有变化,使用update参数
            .update()
          //提交任务
            .apply()

        })

      }

    })





    //关闭资源
    session.stop()





  }


  case class AttachRWBureau(
                             AttachRWBureau: String,
                             alldata: Int,
                             atperror: Int,
                             main: Int,
                             wifi: Int,
                             balise: Int,
                             TCR: Int,
                             speed: Int,
                             DMI: Int,
                             TIU: Int,
                             JRU: Int)

}
