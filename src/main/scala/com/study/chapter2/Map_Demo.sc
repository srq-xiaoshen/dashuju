/*import scala.collection.immutable.HashMap

//创建map集合
val map  = Map("zhang"->23,"li"->35,"tie"->66)
//通过key获取value
//第一种方式
val result = map("li")
//打印输出
println(result)

//第二种方式
println(map.get("tie1"))
//第三种获取方式
println(map.getOrElse("zhang1", 99))

//第二种创建不可变map集合
val map1 = new HashMap[String,Int]()*/


//创建可变map集合
import scala.collection.mutable
import scala.collection.mutable._
val map = Map("lufei"->12,"nvdi"->13,"sandaoliu"->45)
map.put("luobin",34)
//打印输出
println(map)
map+=(("shanzhi",33))
//打印输出
println(map)
map+=("qiaoba"->10)
//打印输出
println(map)


//第二种创建可变map集合
val map1 = new mutable.HashMap[String,Int]()
//添加元素
map1.put("kaput",78)
//打印输出
println(map1)
//添加元素
map1+=(("zhanguo",79))
//打印输出
println(map1)
//添加元素
map1+=("he"->80)
//打印输出
println(map1)

//删除元素
map1-=("he")
//打印输出
println(map1)
map1.remove("kaput")
println(map1)




















