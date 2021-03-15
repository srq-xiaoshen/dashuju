import scala.collection.mutable

/*
import scala.collection.immutable.Queue

//创建不可变队列
val q = Queue(1,2,3)
//打印输出
println(q)
//增加元素
val q1 = q.enqueue(4)
//打印输出
println(q)
println(q1)
//增加集合
val q2 = q.enqueue(List(5,6,7))
//打印输出
println(q)
println(q2)
//增加队列
val q3 = q.enqueue(Queue(10,20,30))
println(q3)

//删除元素
val q4 = q3.drop(3)
println(q3)
println(q4)


println(q3.head)
println(q3.tail)
println(q3.last)*/


//创建可变队列
val q = new mutable.Queue[Int]()
//添加元素
q.enqueue(1,2,3)
//打印输出
println(q)
//添加元素
q.+=(4,5,6)
//打印输出
println(q)
//增加集合
q++=List(10,20,30)
println(q)

//弹出队列
q.dequeue()
println(q)
q.dequeue()
println(q)
q.dequeueAll(x=>x>5)
println(q)

















