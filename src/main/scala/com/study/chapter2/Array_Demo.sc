//创建定长数组
//5:规定的是数组当中元素的个数，如果没有赋值，用0占位
val arr = new Array[Int](5)
//给数组赋值
arr(0)=10
//打印输出,调用toBuffer方法，将数组变为数组缓冲
println(arr.toBuffer)

//第二种创建方式
//调用apply方法创建
val arr1 = Array[Int](1,2,3,4)
//打印输出
println(arr1.toBuffer)

//第三种创建方式
//调用apply方法创建
val arr2 = Array(1,2.5,"hello")


