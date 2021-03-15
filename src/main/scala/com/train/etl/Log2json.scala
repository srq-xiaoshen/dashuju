package com.train.etl

import java.text.SimpleDateFormat
import java.util.Date

import com.train.beans.Logs
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

/*
数据预处理
  1.按照规则去处理脏数据
  2.将数据转换成json
 */
object Log2json {
  def main(args: Array[String]): Unit = {
        //创建session
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    //读取数据
    val inputPath : String = "D:\\work\\project\\高铁项目 - 成都0706\\day04\\高铁数据.txt"
    val sourceRDD: RDD[String] = session.sparkContext.textFile(inputPath)
    //导入隐式转换
    import session.implicits._
    /*
    *按照数据规则清洗
    * 1.3OOT和300TATO
    * 2.300s和300SATO
    * 3.200H
    * 4.300H
     */
    //定义格式转换种类
    val format: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    //获取当前系统时间
    val date: Date = new Date()
    val nowTime : Long = date.getTime()
    //对数据长度进行过滤
    val filtedRDD: RDD[Array[String]] = sourceRDD.map(_.split("[|]",-1)).filter(_.length >=55).cache()
    //对300T以及300TATO进行处理
    val rdd300T: RDD[Array[String]] = filtedRDD.filter(arr => arr(0).contains("300T")
      && !arr(17).contains("复位") && !arr(17).contains("SB待机") && !arr(17).contains("常用制动")
      && (nowTime - format.parse(arr(7)).getTime <= 300000000000000000L)
      /*
         AtpError包含 休眠模式”												本条数据不要了
	      AtpError包含“未知”													本条数据不要了
	        AtpError包含“无致命错误”											本条数据不要了
	      AtpError包含“一致性消息错误”										本条数据不要了
	        AtpError包含“NVMEM故障”
      */
      && !arr(17).contains("休眠模式")
      && !arr(17).contains("未知")
      && !arr(17).contains("无致命错误")
      && !arr(17).contains("一致性消息错误")
      && !arr(17).contains("NVMEM故障")
    )
    val rdd300TError: RDD[Array[String]] = rdd300T.filter(arr => arr(17).contains("当前ATP系统处于故障中[SF]"))
      .map(arr => arr(0) + "|" +
        arr(1) + "|" +
        arr(2) + "|" +
        arr(3) + "|" +
        arr(4) + "|" +
        arr(5) + "|" +
        arr(6) + "|" +
        arr(7).substring(0, 12) + "00|" +
        arr(8) + "|" +
        arr(9) + "|" +
        arr(10) + "|" +
        arr(11) + "|" +
        arr(12) + "|" +
        arr(13) + "|" +
        arr(14) + "|" +
        arr(15) + "|" +
        arr(16) + "|" +
        arr(17) + "|" +
        arr(18) + "|" +
        arr(19) + "|" +
        arr(20) + "|" +
        arr(21) + "|" +
        arr(22) + "|" +
        arr(23) + "|" +
        arr(24) + "|" +
        arr(25) + "|" +
        arr(26) + "|" +
        arr(27) + "|" +
        arr(28) + "|" +
        arr(29) + "|" +
        arr(30) + "|" +
        arr(31) + "|" +
        arr(32) + "|" +
        arr(33) + "|" +
        arr(34) + "|" +
        arr(35) + "|" +
        arr(36) + "|" +
        arr(37) + "|" +
        arr(38) + "|" +
        arr(39) + "|" +
        arr(40) + "|" +
        arr(41) + "|" +
        arr(42) + "|" +
        arr(43) + "|" +
        arr(44) + "|" +
        arr(45) + "|" +
        arr(46) + "|" +
        arr(47) + "|" +
        arr(48) + "|" +
        arr(49) + "|" +
        arr(50) + "|" +
        arr(51) + "|" +
        arr(52) + "|" +
        arr(53) + "|" +
        arr(54)
      ).distinct()
      .map(_.split("[|]", -1))

      //300S和300SATO
      //AtpError包含“休眠模式”												本条数据不要了
      //Level为“CTCS-3”并且AtpError包含“SB待机”或者包含“切换到备系”
    val rdd300S: RDD[Array[String]] = filtedRDD.filter(arr => arr(0).contains("300S")
      && !arr(17).contains("休眠模式")
      && !(arr(9).contains("CTCS-3") && (arr(17).contains("SB待机") || arr(17).contains("切换到备系")))
    )
    //200H
    //	AtpError包含“休眠模式”												本条数据不要了
    //	AtpError包含“VC2报”
    val rdd200H: RDD[Array[String]] = filtedRDD.filter(arr => arr(0).contains("200H")
      && !arr(17).contains("休眠模式")
      && !arr(17).contains("VC2报")
    )
    //300H
    //	AtpError包含“休眠模式”
    val rdd300H: RDD[Array[String]] = filtedRDD.filter(arr => arr(0).contains("300H")
      && !arr(17).contains("休眠模式")
    )
    val otherRDD: RDD[Array[String]] = filtedRDD.filter(arr => !arr(0).contains("300T")
      && !arr(0).contains("300S")
      && !arr(0).contains("200H")
      && !arr(0).contains("300H")
    )

    val eltRDD: RDD[Array[String]] = rdd300T.filter(arr => !arr(17).contains("当前ATP系统处于故障中[SF]"))
      .union(rdd300TError)
      .union(rdd300S)
      .union(rdd300H)
      .union(rdd200H)
      .union(otherRDD)


    //将数据转换成json =  schema + row
    //将rdd转换成dataframe
    //1.方式1, 元祖.toDF() 字段太多
    //2.方式2, session.createdataframe
    /*val rowRDD: RDD[Row] = eltRDD.map(arr => Row(
      arr(0),
      arr(1),
      arr(2),
      arr(3),
      arr(4),
      arr(5),
      arr(6),
      arr(7),
      arr(8),
      arr(9),
      arr(10),
      arr(11),
      arr(12),
      arr(13),
      arr(14),
      arr(15),
      arr(16),
      arr(17),
      arr(18),
      arr(19),
      arr(20),
      arr(21),
      arr(22),
      arr(23),
      arr(24),
      arr(25),
      arr(26),
      arr(27),
      arr(28),
      arr(29),
      arr(30),
      arr(31),
      arr(32),
      arr(33),
      arr(34),
      arr(35),
      arr(36),
      arr(37),
      arr(38),
      arr(39),
      arr(40),
      arr(41),
      arr(42),
      arr(43),
      arr(44),
      arr(45),
      arr(46),
      arr(47),
      arr(48),
      arr(49),
      arr(50),
      arr(51),
      arr(52),
      arr(53),
      arr(54)
    )
    )
    val dataframe: DataFrame = session.createDataFrame(rowRDD,LogSchema.schema)*/

    //样例类
    val LogsRDD: RDD[Logs] = eltRDD.map(arr => Logs(
      arr(0),
      arr(1),
      arr(2),
      arr(3),
      arr(4),
      arr(5),
      arr(6),
      arr(7),
      arr(8),
      arr(9),
      arr(10),
      arr(11),
      arr(12),
      arr(13),
      arr(14),
      arr(15),
      arr(16),
      arr(17),
      arr(18),
      arr(19),
      arr(20),
      arr(21),
      arr(22),
      arr(23),
      arr(24),
      arr(25),
      arr(26),
      arr(27),
      arr(28),
      arr(29),
      arr(30),
      arr(31),
      arr(32),
      arr(33),
      arr(34),
      arr(35),
      arr(36),
      arr(37),
      arr(38),
      arr(39),
      arr(40),
      arr(41),
      arr(42),
      arr(43),
      arr(44),
      arr(45),
      arr(46),
      arr(47),
      arr(48),
      arr(49),
      arr(50),
      arr(51),
      arr(52),
      arr(53),
      arr(54)
    ))
    //session.createDataFrame(LogsRDD).show()
    val dataframe: DataFrame = LogsRDD.toDF()
    //写成json
    val output : String = "D:\\work\\project\\高铁项目 - 成都0706\\day01\\etl";
    /*
    *使用分区算子可以重新分区
    * 1.repartition,重新分区,底层调用的是coalesce,但是他是支持shuffle的,所以一般情况下少分区变多分区使用
    * 2.coalesce,重新分区,可以选择是否进行shuffle过程的,所以一般情况下用在多分区变少分区的情况
    *3.partitionBy,重新分区,里面传入的是分区器,dataframe中传某个字段,就会按照这个字段的数据值进行分区
     */
    //mode Overwrite覆盖,默认情况下是有文件则报错
    dataframe.coalesce(1).write.mode(SaveMode.Overwrite).json(output)



  }
}
