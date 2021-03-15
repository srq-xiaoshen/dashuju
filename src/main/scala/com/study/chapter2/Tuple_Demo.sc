val tup=(1,2.5,12L,"hello",true,18)
//多数情况下，元素下角标从1开始
println(tup._1)
println(tup._4)
//想要元素下角标从0开始
println(tup.productElement(2))
//查看元组当中一共有多少个元素
println(tup.productArity)

//遍历元组
for (i<-tup.productIterator){
  println(i)
}
//函数遍历
tup.productIterator.foreach(println)
