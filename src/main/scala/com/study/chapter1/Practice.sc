import scala.util.control.Breaks._
//此时break相当于continue方法
for (i<- 1 to 10){
  breakable{
    if (i==5){
      break()
    }
    println(i)
  }
}
//此时，break相当于break方法
breakable{
  for (i<- 1 to 10){
    if (i==6){
      break()
    }
    println(i)
  }
}
