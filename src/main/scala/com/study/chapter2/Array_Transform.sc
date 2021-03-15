//val arr = Array(1,2,3,4,5,6,7,8)
/*
//将数组当中的元素扩大5倍
val result = for (i<-arr) yield i*5
//打印输出
println(result.toBuffer)

//利用函数将数组当中元素扩大10倍
//val results = arr.map((x:Int)=>x*10)
//开始简化
//val results = arr.map((x)=>x*10)
//val results = arr.map(x=>x*10)
val results = arr.map(_*10)
//打印输出
println(results.toBuffer)*/

/*//需求：过滤出来偶数
for (i<-arr if i%2==0){
  println(i)
}
//利用函数过滤
//过滤：filter：过滤出来我们想要的结果，摒弃掉不要的结果
//val result = arr.filter((x:Int)=>x%2==0)
//简化
val result = arr.filter(_%2==0)
//打印输出
println(result.toBuffer)*/


//需求：只要偶数，并扩大20倍保存
val arr = Array(1,2,3,4,5,6,7,8)
val result = arr.filter(_%2==0).map(_*20)
//打印输出
println(result.toBuffer)











