package com.scalademo

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{ SparkContext}

object Demo02  {
  def main(args: Array[String]): Unit = {
/*val conf: SparkConf = new SparkConf().setAppName("aa").setMaster("local[*]")
val sc: SparkContext = new SparkContext(conf)
    val data: RDD[String] = sc.textFile("D:\\study\\workcount.txt")
    val data1: RDD[(String, Int)] = data.flatMap(_.split(" ")).map((_,1))
    val result: RDD[(String, Int)] = data1.reduceByKey(_+_)
    result.foreach(println)*/
    val spark: SparkSession = SparkSession.builder().appName("aa").master("local[*]").getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")
    import spark.implicits._

    val data: RDD[String] = sc.textFile("D:\\study\\person.txt")
    val data1: RDD[Array[String]] = data.map(_.split(" "))
    //指定字段类型
    val data2: RDD[(Int, String, Int)] = data1.map(x=>(x(0).toInt,x(1),x(2).toInt))
    /*
    //转换为dataFrame,匹配字段名
    val dataDF: DataFrame = data2.toDF("id","name","age")
      //导入数据库 会自动创建表
     dataDF.write.format("jdbc")
        .option("url","jdbc:mysql://hadoop003:3306/srq")
          .option("dbtable","test1")
          .option("user","root")
          .option("password","000000")
          .save()
      //读取数据库中的内容
      spark.read.format("jdbc")
        .option("url","jdbc:mysql://hadoop003:3306/srq")
          .option("dbtable","test1")
          .option("user","root")
          .option("password","000000")
          .load()
  //使用sql时必须注册表*/
    val dataDF: DataFrame = data2.toDF()
    dataDF.createOrReplaceTempView("test")
    spark.sql("select * from test").show()
}
}
