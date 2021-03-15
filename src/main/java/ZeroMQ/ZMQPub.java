package ZeroMQ;


import org.zeromq.ZMQ;

/*
* 创建服务端 （发布）  pub
* 1.发布端只能发布数据，不能接收数据
* 2.发布端发送数据，不管是否有订阅端订阅数据，都进行数据发送
* 3.造成先于订阅端订阅之前的数据丢失
**/
public class ZMQPub {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket pub = context.socket(ZMQ.PUB);
        pub.bind("tcp://localhost:5555");
        boolean flag = true ;
        int i = 0 ;
        while (flag){
            Thread.sleep(1000);
            pub.send("hello word" + i);
            i++;
        }
        pub.close();
        context.close();
    }
}
