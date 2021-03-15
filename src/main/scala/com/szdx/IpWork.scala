package szdx

import com.utils.SubString
import org.apache.commons.lang.StringUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object IpWork {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName(this.getClass.getName)
    val sc: SparkContext = new SparkContext(sparkConf)
    //输入
    val sourceData: RDD[String] = sc.textFile(args(0))
    val arrRdd: RDD[Array[String]] = sourceData.map(_.split(";")).filter(x=>x.length>28).cache()
    //Apple
    val arr1: RDD[Array[String]] = arrRdd.
      filter(arr =>
        arr(6).contains("Apple")
          && arr(25).length>=7
          && arr(25).length<=15
          && arr(22).length>12
      )
    val idfaCount: RDD[((String, String), Int)] = arr1.map(arr1 =>
      ((SubString.subStringStartToEnd(arr1(22)), SubString.subStringStartToEnd(arr1(25))), 1)
      //(e12312s414h23,1.1.2.3,1) (ea242ha231313,1.1.2.3,1)
    )
/*    val result: RDD[(String, String, Int)] = idfaCount
      .reduceByKey(_+_)
      .map(x=>(x._1._1,x._1._2,x._2))
      .sortBy(_._3)*/

    //Android
    val arr2: RDD[Array[String]] = arrRdd.
      filter(arr =>
        !arr(6).contains("Apple")
          && StringUtils.isNotEmpty(arr(6))
          && arr(20).length<50
          && arr(20).length>12
          && arr(27).length>=7
          && arr(27).length<=15
      )
    val imeiCount: RDD[((String, String), Int)] = arr2.map(arr1 =>
      ((SubString.subStringStartToEnd(arr1(20)), SubString.subStringStartToEnd(arr1(27))), 1)
      //(4234236423542,1.1.2.3,1) (223538453746,1.1.2.3,1)
    )

/* val result: RDD[(String, String, Int)] = imeiCount
      .reduceByKey(_+_)
      .map(x=>(x._1._1,x._1._2,x._2))
      .sortBy(_._3)*/
    //输出
    val result: RDD[(String, String, Int)] = idfaCount.
      union(imeiCount)
      .reduceByKey(_+_)
      .map(x=>(x._1._1,x._1._2,x._2))
      .sortBy(_._3)
    result.saveAsTextFile(args(1))

  }
}
