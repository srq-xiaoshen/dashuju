package com.szdx

import com.utils.SubString
import org.apache.commons.lang.StringUtils
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object IpWorkSQL {
  def main(args: Array[String]): Unit = {
    val session: SparkSession = SparkSession.builder().appName(this.getClass.getName).master("local[*]").getOrCreate()
    val sc: SparkContext = session.sparkContext
    sc.setLogLevel("WARN")

//    val inputPath="D:\\学习\\tmp\\device.log.2020-12-01-09"
    val inputPath="D:\\tmp\\output\\ipoutput"
    val outputPath="D:\\tmp\\output\\ipoutputSQL"
    import session.implicits._

    val sourceData: RDD[String] = sc.textFile(inputPath)
    val arrRdd: RDD[Array[String]] = sourceData.map(_.split(";")).filter(x=>x.length>28).cache()

    //Apple
    val arr1: RDD[Array[String]] = arrRdd.
      filter(arr =>
        arr(6).contains("Apple")
          && arr(25).length>=7
          && StringUtils.isNotEmpty(arr(22))
      )
    val idfaCount: RDD[((String, String), Int)] = arr1.map(arr1 =>
      ((SubString.subStringStartToEnd(arr1(22)), SubString.subStringStartToEnd(arr1(25))), 1)
      //(e12312s414h23,1.1.2.3,1) (ea242ha231313,1.1.2.3,1)
    )

    val value: RDD[((String, String), Int)] = idfaCount.reduceByKey(_+_)


    //Android
    val arr2: RDD[Array[String]] = arrRdd.
      filter(arr =>
        !arr(6).contains("Apple")
          && StringUtils.isNotEmpty(arr(6))
          && arr(20).length<50
          && StringUtils.isNotEmpty(arr(20))
          && arr(27).length>=7
      )
    val imeiCount: RDD[((String, String), Int)] = arr2.map(arr1 =>
      ((SubString.subStringStartToEnd(arr1(20)), SubString.subStringStartToEnd(arr1(27))), 1)
      //(4234236423542,1.1.2.3,1) (223538453746,1.1.2.3,1)
    )

     val rddResult: RDD[((String, String), Int)] = idfaCount.union(imeiCount).reduceByKey(_+_)


    val dataFrame: DataFrame = rddResult.map(x=>(x._1._1,x._1._2,x._2)).toDF("UUID","ip","sum")
    val result: Dataset[Row] = dataFrame.sort("sum")

    result.write.json(outputPath)

  }

}
