import scala.collection.mutable.ArrayBuffer

//创建定长数组
val arr = Array(1,2,3,4)
//创建变长数组
val buffer = ArrayBuffer(5,6,7)
//将定长数组转换为变长数组
val finalArr = arr.toBuffer
//增加元素
finalArr.append(8,9)
//打印输出
println(finalArr)
//将变长数组转换为定长数组
val finalBuffer = buffer.toArray
