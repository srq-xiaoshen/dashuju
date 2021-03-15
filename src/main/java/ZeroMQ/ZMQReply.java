package ZeroMQ;


import org.zeromq.ZMQ;

/**
 *
 * 创建ZeroMQ服务端
 *1.一问一答，不能多问一答，也不能一问多答
 * 2.先启动客户端与先启动服务端的顺序，不会造成数据丢失。
 *
 *
 * */

public class ZMQReply {
    public static void main(String[] args) {
        //创建ZMQ实例
        ZMQ.Context context = ZMQ.context(1);
        //创建服务端实例
        ZMQ.Socket rep = context.socket(ZMQ.REP);
        //服务端绑定一个IP和端口
        rep.bind("tcp://localhost:5555");
        //建立循环 接收请求
        //当前线程不断开  执行循环
        int i = 0 ;
        while(!Thread.currentThread().isInterrupted()){
            //接收客户端发送的请求（问）
            byte[] recv = rep.recv();
            System.out.println(new String(recv));
            //发送答复
            String line = "word" + i ;
            rep.send(line);
            //发送第二次答复
            //
            rep.send("第二次答复");
            i++;
        }
        //关闭资源
        rep.close();
        context.close();
    }
}
