val arr = Array(1,2,3,4,"hello")
//第一种遍历
for (i<- arr){
  println(i)
}

//第二种遍历
for (i<-0 until(arr.length)){
  println(arr(i))
}

//第三种遍历
arr.foreach(x=>println(x))
//简化
//神奇的下划线
arr.foreach(println(_))
arr.foreach(println)