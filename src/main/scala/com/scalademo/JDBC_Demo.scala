package com.scalademo

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.streaming.OutputMode

object JDBC_Demo {
  def main(args: Array[String]): Unit = {
    //创建程序入口
    val spark: SparkSession = SparkSession.builder().appName("demo").master("local[*]").getOrCreate()
    //调用sparkContext
    val sc: SparkContext = spark.sparkContext
    //设置日志级别
    sc.setLogLevel("WARN")
    //导包
    import spark.implicits._
    //连接kafka
    val wordsDF: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "node01:9092,node02:9092,node03:9092")
      .option("subscribe", "test")
      .load()
    //获取kafka当中的数据并转换为DataSet
    val file: Dataset[String] = wordsDF.selectExpr("CAST(value AS STRING)").as[String]
    //切分数据
    val spliFile: Dataset[String] = file.flatMap(_.split(" "))
    //统计结果
    val result: Dataset[Row] = spliFile.groupBy("value").count().sort($"count".desc)
    //创建类的对象
    val write = new JDBCSink("jdbc:mysql://localhost:3306/bigdata","root","root")
    //将结果数据写出
    result.writeStream
      .format("jdbc")
      .foreach(write)
      .outputMode(OutputMode.Complete())
      .start()
      .awaitTermination()

  }
}
class JDBCSink(url:String,username:String,password:String) extends ForeachWriter[Row] with Serializable{
  var connection:Connection = _ //_表示占位符,后面会给变量赋值
  var preparedStatement: PreparedStatement = _
  //开启连接
  override def open(partitionId: Long, version: Long): Boolean = {
    connection = DriverManager.getConnection(url, username, password)
    true
  }

  /*
  CREATE TABLE `t_word` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `word` varchar(255) NOT NULL,
      `count` int(11) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `word` (`word`)
    ) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
   */
  //replace INTO `bigdata`.`t_word` (`id`, `word`, `count`) VALUES (NULL, NULL, NULL);
  //处理数据--存到MySQL
  override def process(row: Row): Unit = {
    val word: String = row.get(0).toString
    val count: String = row.get(1).toString
    println(word+":"+count)
    //REPLACE INTO:表示如果表中没有数据这插入,如果有数据则替换
    //注意:REPLACE INTO要求表有主键或唯一索引
    val sql = "REPLACE INTO `t_word` (`id`, `word`, `count`) VALUES (NULL, ?, ?);"
    preparedStatement = connection.prepareStatement(sql)
    preparedStatement.setString(1,word)
    preparedStatement.setInt(2,Integer.parseInt(count))
    preparedStatement.executeUpdate()
  }

  //关闭资源
  override def close(errorOrNull: Throwable): Unit = {
    if (connection != null){
      connection.close()
    }
    if(preparedStatement != null){
      preparedStatement.close()
    }
  }

}