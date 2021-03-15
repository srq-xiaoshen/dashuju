/*
//val 函数名=（参数名：参数类型，参数名：参数类型）=>{函数体}
val fun=(x:Int,y:String)=>{
  x+y
}
//给函数赋值
val result = fun(100,"lisi")
//打印输出
println(result)

//val 函数名：（参数类型，参数类型）=>返回值类型={函数体}
val fun1:(Int,String)=>(String)={
  (x,y)=>"wangermazi"
}
//给函数赋值
val results = fun1(250,"wangwu")
//打印输出
println(results)
*/



/*/*
//def 方法名（参数名：参数类型，参数名：参数类型）={方法体}
def hello(x:Int,y:String)={
  x+y
}
//给方法赋值
val result = hello(20,"world")
//打印输出
println(result)

//def 方法名（参数名：参数类型，参数名：参数类型）：返回值类型={方法体}
def meth(first:Int,second:String):String={
  first+second
}
//给方法赋值
val result1 = meth(100,"hello")
//打印输出
println(result1)
*/


/*//创建方法
def hello(x:String,y:Int=20)={
  x+y
}
//给方法赋值
val result = hello("linshiyin",200)
//打印输出
println(result)*/

//创建方法
def hello(x:String,y:Int*)={
  var sum = 0
  for (i<- y){
    sum+=i
  }
  println(sum)
}
//给方法赋值
val result = hello("zhangsan",10,20,30,40,50)
//打印输出
println(result)*/



/*//定义一个方法
def m0(f:(Int,Int)=>Int)={
  f(2,6)
}
//定义一个函数
val fun=(x:Int,y:Int)=>{
  x*y
}
//函数作为参数传递到方法中去
val result = m0(fun)
//打印输出
println(result)*/



//方法就是函数
//定义一个方法
def hello(x:Int,y:String,z:Double)={
  x+y
}
//将方法转换为函数
val result = hello _
//打印输出
println(result)








