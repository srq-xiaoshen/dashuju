import scala.collection.mutable.ListBuffer

/*
//创建List集合
//调用的apply方法创建
//默认创建的集合都是不可变集合
val lst = List(1,2,3)
val lst1 = 4::lst
//打印输出
println(lst)
println(lst1)
val lst2 = lst:+5
println(lst)
println(lst2)
val lst3=List(7,8,9)
//将两个集合进行整合
val result = lst++lst3
//打印输出
println(lst)
println(result)
*/


/*//创建可变的list集合
val lst = ListBuffer[Int](1,2,3,4)
//打印输出
println(lst)
//添加元素
lst.append(5,6,7)
//打印输出
println(lst)
lst.+=(9)
//打印输出
println(lst)

//第二种创建方式
val lst2 = new ListBuffer[Int]()
lst2.append(1,2,3)
println(lst2)

//整合两个集合
lst++=lst2
//打印输出
println(lst)*/


/*//遍历list集合
val lst = List(1,2,3,4,5)
//第一种遍历
for (i<- lst){
  println(i)
}
//第二种遍历
for (i<- 0 to lst.length-1){
  println(lst(i))
}
//第三种遍历
lst.foreach(println)*/



/*val lst = List(1,2,3,4,5)
println(lst.map(_ * 5))
println(lst.filter(_ % 2 == 0))*/

/*val lst = List("chen","li","zhang","huang","he")
//第一个过滤
println(lst.filter(_.startsWith("z")))
//第二个过滤
println(lst.filter(_.endsWith("i")))
//第三个过滤
println(lst.filter(_.contains("a")))*/


val lst = List(1,2,3,4,5)
val result = lst.filter(x => if (x % 2 == 0) {
  true
} else {
  false
})
println(result)







