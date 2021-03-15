import scala.collection.mutable.ArrayBuffer

/*import scala.collection.mutable.ArrayBuffer
//变长数组的创建
val arr = new ArrayBuffer[Int]()
//打印输出
println(arr)
//增加元素
arr.append(1,2,3,4,5)
//打印输出
println(arr)

//第二种创建方式
val arr1 = ArrayBuffer[Int](1,2,3,4)
//给数组增加元素
arr1+=(5,6,7)
//打印输出
println(arr1)

//第三种创建方式
val arr2 = ArrayBuffer(1,2,3)
//打印输出
println(arr2)

//数组相加
arr1++=arr2
//打印输出
println(arr1)

//给arr2增加元素
arr2.+=(4,5)
println(arr2)*/



//创建数组
val arr = ArrayBuffer[Int](1,2,3)
//打印输出
println(arr)
//在指定的位置添加元素
arr.insert(0,0,100,200)
//打印输出
println(arr)

//删除指定位置的元素
arr.remove(1)
//打印输出
println(arr)
arr.remove(2,3)
println(arr)








