import scala.collection.mutable

/*//可变set集合
//调用apply方法创建
import scala.collection.mutable._
val set = Set(1,2,3)
//添加元素
set.add(4)
//打印输出
println(set)
set+=(5,6,7)
println(set)*/

val set = new mutable.HashSet[Int]()
//添加元素
set.add(1)
println(set)
set.+=(2,3,4,5)
println(set)
val set1 = Set(10,20,30)
//整合两个集合
set++=set1
//打印输出
println(set)
//删除元素
set.remove(10)
//打印输出
println(set)
set-=(20,30,1)
println(set)






















/*import scala.collection.immutable.HashSet
//创建不可变set集合
//调用apply方法创建
/*val set = Set(1,2,3)
//打印输出
println(set)
//添加元素
val set1 = set+4
//打印输出
println(set)
println(set1)
val set2 = Set(7,8,9)
//整合两个集合
val result = set++set2
//打印输出
println(result)*/

//第二种创建方式
val set = new HashSet[Int]()
val set1 = set.+(1,2,3)
println(set)
println(set1)
//减少元素
val result = set1-2
//打印输出
println(set1)
println(result)*/














