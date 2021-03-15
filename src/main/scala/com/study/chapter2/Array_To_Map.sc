/*
val arr = Array(("zhangsan",25),("lisi",26),("wangwu",35))
//将成对出现的数组转换为map集合
val result = arr.toMap
//打印输出
println(result)*/


//拉链操作
val arr1 = Array("mingren","chutian","xiaoying","zuozhu","zilaiye","gangshou")
val arr2 = Array(12,11,35)
/*
//通过zip进行拉链操作
val result = arr1.zip(arr2)
//打印输出
println(result.toBuffer)
*/

/*//zipAll
val result = arr1.zipAll(arr2,"dashewan",99)
//打印输出
println(result.toBuffer)*/


//zipWithIndex
println(arr1.zipWithIndex.toBuffer)







