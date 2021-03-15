val map = Map("xiaoming"->12,"xiaohong"->13,"fage"->45)
//就遍历key
for (k<-map.keys){
  println(k)
}
//就遍历value
for (v<-map.values){
  println(v)
}

//遍历key和value
for ((k,v)<-map){
  println(k)
  println(v)
}

for (x<-map){
  println(x._1)
  println(x._2)
}

//foreach遍历
map.foreach(println)