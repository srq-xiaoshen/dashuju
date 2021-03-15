package com.szdx

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object JoinTest {
  var uuid=1611309207872L
  def main(args: Array[String]): Unit = {
    val session: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .config("spark.debug.maxToStringFields", "50")
      .master("local[*]")
      .getOrCreate()
    val sc: SparkContext = session.sparkContext
    sc.setLogLevel("WARN")
    import session.implicits._
    val frameSource: DataFrame = session.read.json("D:\\tmp\\adTest008\\result.json")
    val data1: Dataset[(Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long)] = frameSource.map(x => {
      val uuid: Long = x.getAs("uuid1")
      val id1: Long = x.getAs("t1id1")
      val id2: Long = x.getAs("t1id2")
      val id3: Long = x.getAs("t1id3")
      val id4: Long = x.getAs("t1id4")
      val id5: Long = x.getAs("t1id5")
      val id6: Long = x.getAs("t1id6")
      val id7: Long = x.getAs("t1id7")
      val id8: Long = x.getAs("t1id8")
      val id9: Long = x.getAs("t1id9")
      val id10: Long = x.getAs("t1id10")
      val id11: Long = x.getAs("t1id11")
      val ts1: Long = x.getAs("ts1")
      (uuid, id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, ts1)
    })
    data1
   /* val data1: Dataset[(Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long)] = frameSource.map(x => {
      uuid = uuid + 1
      val id1: Long = x.getAs("t1id1")
      val id2: Long = x.getAs("t1id2")
      val id3: Long = x.getAs("t1id3")
      val id4: Long = x.getAs("t1id4")
      val id5: Long = x.getAs("t1id5")
      val id6: Long = x.getAs("t1id6")
      val id7: Long = x.getAs("t1id7")
      val id8: Long = x.getAs("t1id8")
      val id9: Long = x.getAs("t1id9")
      val id10: Long = x.getAs("t1id10")
      val id11: Long = x.getAs("t1id11")
      val ts1: Long = x.getAs("ts1")
      val id21: Long = x.getAs("t2id1")
      val id22: Long = x.getAs("t2id2")
      val id23: Long = x.getAs("t2id3")
      val id24: Long = x.getAs("t2id4")
      val id25: Long = x.getAs("t2id5")
      val id26: Long = x.getAs("t2id6")
      val id27: Long = x.getAs("t2id7")
      val id28: Long = x.getAs("t2id8")
      val id29: Long = x.getAs("t2id9")
      (uuid, id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, ts1, id21, id22, id23, id24, id25, id26, id27, id28, id29)
    })
     val frame: DataFrame = data1.toDF("uuid", "id1", "id2", "id3", "id4", "id5", "id6", "id7", "id8", "id9", "id10", "id11", "ts1",
      "id21", "id22", "id23", "id24", "id25", "id26", "id27", "id28", "id29")
    */
    data1
    val frame1: DataFrame = data1
      .toDF("uuid","id1", "id2", "id3","id4", "id5", "id6","id7", "id8", "id9","id10","id11","ts1")
    frame1.createTempView("table1")
    val frame2: DataFrame = data1
      .toDF("uuid","id1", "id2", "id3","id4", "id5", "id6","id7", "id8", "id9","id10","id11","ts1")
    frame2.createTempView("table2")

    val result: DataFrame = session.sql(
      """select t1.uuid uuid1,
        |t1.id1 t1id1,t1.id2 t1id2,t1.id3 t1id3,
        |t1.id4 t1id4,t1.id5 t1id5,t1.id6 t1id6,t1.id7 t1id7,t1.id8 t1id8,
        |t1.id9 t1id9,t1.id10 t1id10,t1.id11 t1id11,
        |t1.ts1 ts1,
        |t2.id1 t2id1,t2.id2 t2id2,t2.id3 t2id3,
        |t2.id4 t2id4,t2.id5 t2id5,t2.id6 t2id6,t2.id7 t2id7,t2.id8 t2id8,
        |t2.id9 t2id9,t2.id10 t2id10,t2.id11 t2id11,
        |t2.ts1 ts2
        |from table1 t1 left join table2 t2 on
        |t1.uuid=t2.uuid
        |
        |""".stripMargin)
    result.coalesce(1).write.json("D:\\tmp\\adTest009")
    session.stop()

  }

}
