package ZeroMQ;


import org.zeromq.ZMQ;

/*
* 创建客户端拉数据
*
*
* */
public class ZMQPull {
    public static void main(String[] args) {
        //创建ZMQ实例
        ZMQ.Context context = ZMQ.context(1);
        //创建拉实例
        ZMQ.Socket pull = context.socket(ZMQ.PULL);
        //拉实例创建连接
        pull.connect("tcp://localhost:5555");
        while (!Thread.currentThread().isInterrupted()){
            //拉数据
            byte[] recv = pull.recv();
            System.out.println(new String(recv));

            //模拟pull端发送数据
            pull.send("XXXX");

        }
        pull.close();
        context.close();
    }
}
