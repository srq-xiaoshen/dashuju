package com.train.report

import scalikejdbc.{DB, SQL}
import scalikejdbc.config.DBs


//scalikejdbc ,使用scalikejdbc不论增删改查(CRUD),都可以使用,表要提前存在
//使用方便简单,不需要手动去维护线程
object ScalikeJdbcTest {
  def main(args: Array[String]): Unit = {
    //加载application.conf 文件中的jdbc的配置
    DBs.setup()

    //表中插入数据
   /* DB.autoCommit{ implicit session =>
      //写SQL 语句
      SQL("insert into student values (?,?,?)")
        //绑定参数
        .bind(23,"zhangsan",18)
        //执行代码 ,只要是表发生了变化就要用update ,增删改都需要
        .update()
      //提交任务
        .apply()
    }*/

    //删除数据
   /* DB.autoCommit{implicit session =>
      SQL("delete from student where id = ?")
        //绑定参数
        .bind(23)
        //执行代码 ,只要是表发生了变化就要用update ,增删改都需要
        .update()
        //提交任务
        .apply()
    }*/


    //修改数据
    /*DB.autoCommit{implicit session =>
      SQL("update student set name = ? where id = ?")
        //绑定参数
        .bind("train",22)
      //执行代码 ,只要是表发生了变化就要用update ,增删改都需要
        .update()
        //提交任务
        .apply()*/

    //查询数据,在mysql中查询数据不需要执行commit命令

/*    val list: List[(Int, String, Int)] = DB.readOnly { implicit session =>
      //写SQL
      SQL("select * from student")
        .map(wrs => (
          wrs.int("id"),
          wrs.string("name"),
          wrs.int("age")
        ))
        //不需要执行update
        //将所采集的数据转换成元组形式放入列表中
        .list()
        //提交任务
        .apply()
    }
    list.foreach(println)*/


    //如果我要插入5条数据,插入第三条时出现了故障,前天条实际已经插入表中了,不能对全体数据加载事务
    //如果数据量天大,很难确定具体是程序执行到哪一条出现了问题
    /*for (i <- (1 to 5)){
      if(i == 3){
        i/0
      }
      DB.autoCommit{implicit  session =>
        SQL("insert into student values (?,?,?)")
          .bind(30+i,"zhangsan",18)
          .update()
          .apply()
      }
    }*/

    //整体加事务
    DB.localTx{implicit  session =>
      for ( i <- 1 to 5){
        if(i == 3){
          i/0
        }
        SQL("insert into student values (?,?,?)")
          .bind(40+i,"lisi",20)
          .update()
          .apply()
      }
    }




    }



}
