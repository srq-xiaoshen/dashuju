package ZeroMQ;


import org.zeromq.ZMQ;

/**
 *
 * 代理模式
 * 1.代理可以看作服务端的客户端
 * 2.也可以看作客户端的服务端
 *
 * */
public class ZMQPubProxySub {

    public static void main(String[] args) {
        //创建ZMQ的实例
        ZMQ.Context context = ZMQ.context(MyZMQ.MyThreadNum);
        //创建代理的客户端
        ZMQ.Socket sub = context.socket(ZMQ.SUB);
        //绑定IP和端口号
        sub.connect("tcp://localhost:5555");
        //订阅一个主题
        sub.subscribe("".getBytes());
        //创建代理的服务端
        ZMQ.Socket pub = context.socket(ZMQ.PUB);
        //绑定IP和端口号
        pub.bind("tcp://localhost:5558");
        //启动代理，
        ZMQ.proxy(sub,pub,null);
        sub.close();
        pub.close();
        context.close();

    }
}
