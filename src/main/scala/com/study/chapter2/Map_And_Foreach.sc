/*
//创建函数
val fun = (x:Int)=>{x+1}
//创建list集合
val lst = List(1,2,3,4,5)
//foreach
val a = lst.foreach(fun)
//map
val b = lst.map(fun)
//查看类型
println(a.getClass)
println(b.getClass)*/



val lst = List(1,2,3,4,5)
//foreach
lst.foreach(x=>println(x*5))
//map
println(lst.map(_ * 5))

















