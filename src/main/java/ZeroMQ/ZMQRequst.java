package ZeroMQ;

import org.zeromq.ZMQ;

/*
 * ZeroMQ问答模式  客户端
 *
 * */


public class ZMQRequst {
    public static void main(String[] args) throws InterruptedException {
        //创建ZMQ实例
        ZMQ.Context context = ZMQ.context(1);
        //创建客户端实例
        //避免魔鬼数字 1 2 3 200  500 520
        ZMQ.Socket req = context.socket(ZMQ.REQ);
        //连接服务器的IP和端口
        req.connect("tcp://localhost:5555");
        //建立死循环  持续发送消息（请求）
        //定义一个变量  赋值布尔类型  用于规避编译错误
        boolean flag = true ;
        while (flag) {
            //发送数据
            String line = "hello ";
            req.send(line);

            //发送第二个请求
            //不可以，因为只能一问一答
            //req.send("第二个请求");
            //接收服务端答复的数据
            //不可以，接收到第一次请求后，无法做出两次答复
            byte[] recv = req.recv();
            System.out.println(new String(recv));
            //线程睡眠
            Thread.sleep(500);
        }
        //关闭资料
        req.close();
        context.close();

    }
}
