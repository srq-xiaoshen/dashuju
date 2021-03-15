package com.train.utils

import org.apache.commons.lang.StringUtils
import org.apache.spark.sql.Row


//存储ATP流式公共方法的类
object MakeATPStreamKPI {
  def makeATPErrorKPI(MATPBaseInfo_AtpError : String) = {
    //判断MATPBaseInfo_AtpError是否为空
    val listError: List[Int] = if (StringUtils.isNotEmpty(MATPBaseInfo_AtpError)){
      //判断对映的AtpError字段是那种类型的报警
      val atpError: List[Int] = if(MATPBaseInfo_AtpError.contains("车载主机")){
        List[Int](1,0,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("无线传输单元")){
        List[Int](0,1,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("应答器信息接收单元")){
        List[Int](0,1,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("轨道电路信息读取器")){
        List[Int](0,1,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("测速测距单元")){
        List[Int](0,1,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("人机交互接口单元")){
        List[Int](0,1,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("列车接口单元")){
        List[Int](0,1,0,0,0,0,0,0)
      }else if(MATPBaseInfo_AtpError.contains("司法记录单元")){
        List[Int](0,1,0,0,0,0,0,0)
      }else {
        List[Int](0,0,0,0,0,0,0,0)
      }
      //MATPBaseInfo_AtpError不为空的情况下,在AtpError数量上加1
      List[Int](1) ++ atpError
    }else {
      //MATPBaseInfo_AtpError为空的情况下,在AtpError数量上写0
      List[Int](0,0,0,0,0,0,0,0,0)
    }
    //总数据量上加1
    List[Int](1) ++ listError

  }

}
