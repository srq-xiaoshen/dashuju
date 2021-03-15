package com.study.chapter1

object Hello {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      if (i == 7) {
        return
      }
      println(i)
    }
  }
}
