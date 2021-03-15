//第一种创建方式
val map = Map("zhangfei"->44,"likui"->55)
println(map)
//第二种创建方式
val map1 = Map(("guanyu",65),("zhaozilong",70))
println(map1)

//获取集合当中的value
println(map("likui"))
println(map1.get("zhaozilong"))
println(map.getOrElse("zhangfei", 99))