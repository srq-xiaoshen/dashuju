package com.train.utils

import org.apache.spark.sql.Row


//获取天气情况的类
object MakeWeather {
  //获取湿度等级
  def getHum(row : Row) ={
    val hum: Double = TurnType.toDouble(row.getAs[String]("Humidity"))
    //判断温度等级
    val humLevel: String = if(hum >=0 && hum <= 10){
      "hum:0~10"
    }else if (hum >10 && hum <=20 ){
      "hum:10~20"
    }else if (hum >20 && hum <=30 ){
      "hum:20~30"
    }else if (hum >30 && hum <=40 ){
      "hum:30~40"
    }else if (hum >40 && hum <=50 ){
      "hum:40~50"
    }else if (hum >50 && hum <=60 ){
      "hum:50~60"
    }else if (hum >60 && hum <=70 ){
      "hum:60~70"
    }else if (hum >70 && hum <=80 ){
      "hum:70~80"
    }else if (hum >80 && hum <=90 ){
      "hum:80~90"
    }else if (hum >90 && hum <=100 ){
      "hum:90~100"
    }else {
      "hum:数据有误"
    }
    humLevel
  }

  //获取温度等级
  def getTem(row : Row) ={
    val tem: Double = TurnType.toDouble(row.getAs[String]("Temperature"))
    //判断湿度等级
    val temLevel: String = if(tem >= -40 && tem <= -30){
      "tem:-40~-30"
    }else if (tem > -30 && tem <= -20 ){
      "tem:-30~-20"
    }else if (tem > -20 && tem <= -10 ){
      "tem:-20~-10"
    }else if (tem > -10 && tem <= 0 ){
      "tem:-10~0"
    }else if (tem > 0 && tem <= 10 ){
      "tem:0~10"
    }else if (tem > 10 && tem <= 20 ){
      "tem:10~20"
    }else if (tem > 20 && tem <= 30 ){
      "tem:20~30"
    }else if (tem > 30 && tem <= 40 ){
      "tem:30~40"
    }else if (tem > 40 && tem <= 50 ){
      "tem:40~50"
    }else {
      "tem:数据有误"
    }
    temLevel
  }

  //获取天气等级
  def getWea(row : Row) ={
    val wea: String = row.getAs[String]("Weather")
    //判断天气等级
    val weaLevel: String = "wea:" + wea
    weaLevel
  }

  //获取速度等级
  def getSpe(row : Row) ={
    val speed: Double = TurnType.toDouble(row.getAs[String]("MATPBaseInfo_Speed"))
    //判断速度等级
    val speLevel: String = if(speed >= 0 && speed <= 50){
      "spe:0~50"
    }else if (speed > 50 && speed <= 100){
      "spe:50~100"
    }else if (speed > 100 && speed <= 1510){
      "spe:100~150"
    }else if (speed > 150 && speed <= 200){
      "spe:150~200"
    }else if (speed > 200 && speed <= 250){
      "spe:200~250"
    }else if (speed > 250 && speed <= 300){
      "spe:250~300"
    }else if (speed > 300 && speed <= 350){
      "spe:300~350"
    }else {
      "spe:数据有误"
    }
    speLevel
  }


}
