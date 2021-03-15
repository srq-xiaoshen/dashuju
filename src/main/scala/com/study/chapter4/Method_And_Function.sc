//def 方法名（参数名：参数类型，参数名：参数类型）={方法体}
//def 方法名（参数名：参数类型，参数名：参数类型）：返回值类型={方法体}
/*def hello(x:Int,y:String)={
  x+y
}
println(hello(10, "hello"))

def method(first:String,second:Int):String={
  println("hello")
  123
  345
  "normal"
}
println(method("world", 120))*/

/*
def m0(f:(Int,Int)=>Int)={
  f(2,6)
}
val fun1 =(x:Int,y:Int)=>{
  x+y
}
println(m0(fun1))*/


//val 函数名：（参数类型）=>返回值类型={函数体}
//val 函数名=（参数名：参数类型）=>{函数体}
/*val fun1:(Int,String)=>String={
  (x,y)=>y
}

println(fun1(10, "lufei"))

val fun2=(x:Int,y:String)=>{
  x+y
}
println(fun2(100, "likui"))*/

/*def hello={

}*/

/*val result = hello _
println(result)

val fun1:(Int,Int)=>Int=hello*/


//第一种，不要求有返回值类型的函数
//val 函数名=（参数名：参数类型,参数名：参数类型）=>{函数体}
val fun1=(x:Int,y:Int)=>{
  3.5
}
val result = fun1(10,20)
println(result)


//val 函数名:(参数类型，参数类型)=>返回值类型={函数体}
val fun2:(Int,Int)=>Int={
  (x,y)=>x+y
}

val result1 = fun2(100,100)
println(result1)


















