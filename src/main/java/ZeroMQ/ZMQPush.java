package ZeroMQ;


import org.zeromq.ZMQ;

/*
* 创建推拉模式中的推（服务端）
* 1.服务端只能发送数据，不能接收数据
* 2.无论服务端客户端哪个先启动，不会造成数据丢失
*
* */
public class ZMQPush {
    public static void main(String[] args) throws InterruptedException {
        //创建ZMQ实例
        ZMQ.Context context = ZMQ.context(1);
        //创建推实例
        ZMQ.Socket push = context.socket(ZMQ.PUSH);
        //绑定IP和端口
        push.bind("tcp://localhost:5555");
        int i = 0 ;
        boolean flag = true;
        while (flag){
            //push端发送数据
            push.send("hello word" + i);

            //接收数据
            byte[] recv = push.recv();


            Thread.sleep(500);
            i++;
        }
        push.close();
        context.close();
    }
}
