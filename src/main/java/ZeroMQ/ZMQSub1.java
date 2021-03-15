package ZeroMQ;


import org.zeromq.ZMQ;

/*
*
* 创建客户端（订阅）
* */
public class ZMQSub1 {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket sub = context.socket(ZMQ.SUB);
        sub.connect("tcp://localhost:5558");
        int a = 1;
        //与其他两种模式不同，订阅端需要订阅主题 （重点）
        sub.subscribe("".getBytes());
        while (!Thread.currentThread().isInterrupted()){
            byte[] recv = sub.recv();
            System.out.println(new String(recv));
        }
        sub.close();
        context.close();
    }
}
