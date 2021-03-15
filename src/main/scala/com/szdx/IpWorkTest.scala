package szdx

import com.utils.SubString
import org.apache.commons.lang.StringUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD



object IpWorkTest {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("a").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(sparkConf)

    /*   val session: SparkSession = SparkSession.builder().appName("test").master("local[*]").getOrCreate()
       val sc: SparkContext = session.sparkContext
       import session.implicits._*/

    val inputpath = "D:\\学习\\tmp\\device.log.2020-12-01-09"
//    val inputpath="D:\\tmp\\ipwork.txt"
    val outputpath = "D:\\tmp\\output\\allIpOut"
    val appleOutPath = "D:\\tmp\\output\\appleIpOut"
    val androidIpOutPath = "D:\\tmp\\output\\androidIpOut"


    val sourceData: RDD[String] = sc.textFile(inputpath)

    val arrRdd: RDD[Array[String]] = sourceData.map(_.split(";")).filter(x => x.length > 28).cache()


    //Apple
/*    val arr1: RDD[Array[String]] = arrRdd.
      filter(arr =>
        arr(6).contains("Apple")
          && arr(22).length>2
          && arr(22).length<=50
          && arr(25).length>=7
          && arr(25).length <= 15

      )
    val idfaCount: RDD[((String, String), Int)] = arr1.map(arr1 =>
      ((SubString.subStringStartToEnd(arr1(22)), SubString.subStringStartToEnd(arr1(25))), 1)
      //(e12312s414h23,1.1.2.3,1) (ea242ha231313,1.1.2.3,1)
    )*/

 /*   val result: RDD[(String, String, Int)] = idfaCount
          .reduceByKey(_+_)
          .map(x=>(x._1._1,x._1._2,x._2))
          .sortBy(_._3)*/

    //Android
    val arr2: RDD[Array[String]] = arrRdd.
      filter(arr =>
        !arr(6).contains("Apple")
          && StringUtils.isNotEmpty(arr(6))
          && arr(20).length<50
          && arr(20).length>2
          && arr(27).length>=9
          && arr(27).length<=15
      )
    val imeiCount: RDD[((String, String), Int)] = arr2.map(arr1 =>
      ((SubString.subStringStartToEnd(arr1(20)),SubString.subStringStartToEnd(arr1(27))), 1)
      //(4234236423542,1.1.2.3,1) (223538453746,1.1.2.3,1)
    )

     val result: RDD[(String, String, Int)] = imeiCount
          .reduceByKey(_+_)
          .map(x=>(x._1._1,x._1._2,x._2))
          .sortBy(_._3)
    //输出
  /*  val result: RDD[(String, String, Int)] = idfaCount.
      union(imeiCount)
      .reduceByKey(_+_)
      .map(x=>(x._1._1,x._1._2,x._2))
      .sortBy(_._3)
*/
    //   println(result.collect().toBuffer)
    result.saveAsTextFile(androidIpOutPath)
  }
}