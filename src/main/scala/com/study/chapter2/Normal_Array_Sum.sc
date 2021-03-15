val arr = Array(1,7,5,6,9,3,0,8)
//求和
println(arr.sum)
//求最小值
println(arr.min)
//求最大值
println(arr.max)
//获取数组当中前N个元素
println(arr.take(3).toBuffer)
//获取数组当中后N个元素
println(arr.takeRight(3).toBuffer)
//指定条件，知道条件不满足
println(arr.takeWhile(x => x < 8).toBuffer)
//求数组的头部元素
println(arr.head)
//获取尾部元素
println(arr.tail.toBuffer)
//获取最后一个元素
println(arr.last)