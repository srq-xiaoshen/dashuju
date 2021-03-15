package DataCollect;


/*
*
* 1.从ZMQ中获取数据
* 2.进行反序列化
* 3.做为kafka的生产者
* */

import ZeroMQ.MyZMQ;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import dms.MATPOuterClass;
import dms.MTransfCtrlOuterClass;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.zeromq.ZMQ;
import utils.WeatherUtil;

import java.util.Properties;

public class ZMQToKafka {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        //创建ZMQ实例
        ZMQ.Context context = ZMQ.context(MyZMQ.MyThreadNum);
        //创建订阅实例
        ZMQ.Socket sub = context.socket(ZMQ.SUB);
        //建立连接
        sub.connect("tcp://localhost:5555");
        //指定主题
        sub.subscribe("".getBytes());


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建kafka生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        //接收数据
        boolean flag = true;
        while (flag){
            //接收数据
            byte[] recv = sub.recv();
            //将数据反序列化成对象
            MTransfCtrlOuterClass.MTransfCtrl mTransfCtrl = MTransfCtrlOuterClass.MTransfCtrl.parseFrom(recv);
            //对象中三个重要的方法  has   get   set
            //判断mTransfCtrl 中 ，当 Msgtype=1时，Data有值
            //不能通过+ + +  拼接字符串
            StringBuffer stringBuffer = new StringBuffer();


            if(mTransfCtrl.hasMsgtype() &&  mTransfCtrl.getMsgtype() == 1 && mTransfCtrl.hasData()){
                //获取data  data中装有MATP中的内容
                ByteString data = mTransfCtrl.getData();
                //将data 解析成MATP对象
                MATPOuterClass.MATP matp = MATPOuterClass.MATP.parseFrom(data);

                String MPacketHead_ATPType = "" ;
                String MPacketHead_TrainID = "" ;
                String MPacketHead_TrainNum = "" ;
                String MPacketHead_AttachRWBureau = "" ;
                String MPacketHead_ViaRWBureau = "" ;
                String MPacketHead_CrossDayTrainNum = "" ;
                String MPacketHead_DriverID = "" ;
                if (matp.hasPacketHead()){
                    //当PacketHead中有数据时 解析出MPacketHead中的数
                    MATPOuterClass.MPacketHead packetHead = matp.getPacketHead();
                    if(packetHead.hasATPType()){
                        MPacketHead_ATPType = String.valueOf(packetHead.getATPType());
                    }
                    if(packetHead.hasTrainID()){
                        MPacketHead_TrainID = String.valueOf(packetHead.getTrainID());
                    }
                    if(packetHead.hasTrainNum()){
                        MPacketHead_TrainNum = String.valueOf(packetHead.getTrainNum());
                    }
                    if(packetHead.hasAttachRWBureau()){
                        MPacketHead_AttachRWBureau = String.valueOf(packetHead.getAttachRWBureau());
                    }
                    if(packetHead.hasViaRWBureau()){
                        MPacketHead_ViaRWBureau = String.valueOf(packetHead.getViaRWBureau());
                    }
                    if(packetHead.hasCrossDayTrainNum()){
                        MPacketHead_CrossDayTrainNum = String.valueOf(packetHead.getCrossDayTrainNum());
                    }
                    if(packetHead.hasDriverID()){
                        MPacketHead_DriverID = String.valueOf(packetHead.getDriverID());
                    }
                }
                String MATPBaseInfo_DataTime = "";
                String MATPBaseInfo_Speed = "";
                String MATPBaseInfo_Level = "";
                String MATPBaseInfo_Mileage = "";
                String MATPBaseInfo_Braking = "";
                String MATPBaseInfo_EmergentBrakSpd = "";
                String MATPBaseInfo_CommonBrakSpd = "";
                String MATPBaseInfo_RunDistance = "";
                String MATPBaseInfo_Direction = "";
                String MATPBaseInfo_LineID = "";
                String MATPBaseInfo_AtpError = "";
                if(matp.hasATPBaseInfo()){
                    MATPOuterClass.MATPBaseInfo atpBaseInfo = matp.getATPBaseInfo();
                    if(atpBaseInfo.hasDataTime()){
                        MATPBaseInfo_DataTime = String.valueOf(atpBaseInfo.getDataTime());
                    }
                    if(atpBaseInfo.hasSpeed()){
                        MATPBaseInfo_Speed = String.valueOf(atpBaseInfo.getSpeed());
                    }
                    if(atpBaseInfo.hasLevel()){
                        MATPBaseInfo_Level = String.valueOf(atpBaseInfo.getLevel());
                    }
                    if(atpBaseInfo.hasMileage()){
                        MATPBaseInfo_Mileage = String.valueOf(atpBaseInfo.getMileage());
                    }
                    if(atpBaseInfo.hasDataTime()){
                        MATPBaseInfo_DataTime = String.valueOf(atpBaseInfo.getDataTime());
                    }
                    if(atpBaseInfo.hasBraking()){
                        MATPBaseInfo_Braking = String.valueOf(atpBaseInfo.getBraking());
                    }
                    if(atpBaseInfo.hasEmergentBrakSpd()){
                        MATPBaseInfo_EmergentBrakSpd = String.valueOf(atpBaseInfo.getEmergentBrakSpd());
                    }
                    if(atpBaseInfo.hasCommonBrakSpd()){
                        MATPBaseInfo_CommonBrakSpd = String.valueOf(atpBaseInfo.getCommonBrakSpd());
                    }
                    if(atpBaseInfo.hasRunDistance()){
                        MATPBaseInfo_RunDistance = String.valueOf(atpBaseInfo.getRunDistance());
                    }
                    if(atpBaseInfo.hasDirection()){
                        MATPBaseInfo_Direction = String.valueOf(atpBaseInfo.getDirection());
                    }
                    if(atpBaseInfo.hasLineID()){
                        MATPBaseInfo_LineID = String.valueOf(atpBaseInfo.getLineID());
                    }
                    if(atpBaseInfo.hasATPError()){
                        MATPBaseInfo_AtpError = String.valueOf(atpBaseInfo.getATPError());
                    }
                }

                //获取经纬度
                String lng = String.valueOf(matp.getRunNextSignal().getLongitude());
                String lat = String.valueOf(matp.getRunNextSignal().getLatitude());

                //天气信息通过第三的接口获得
                String weather = WeatherUtil.getWeather(lng, lat);

                stringBuffer.append(MPacketHead_ATPType).append("|")
                        .append(MPacketHead_TrainID).append("|")
                        .append(MPacketHead_TrainNum).append("|")
                        .append(MPacketHead_AttachRWBureau).append("|")
                        .append(MPacketHead_ViaRWBureau).append("|")
                        .append(MPacketHead_CrossDayTrainNum).append("|")
                        .append(MATPBaseInfo_DataTime).append("|")
                        .append(MATPBaseInfo_Speed).append("|")
                        .append(MATPBaseInfo_Level).append("|")
                        .append(MATPBaseInfo_Mileage).append("|")
                        .append(MATPBaseInfo_Braking).append("|")
                        .append(MATPBaseInfo_EmergentBrakSpd).append("|")
                        .append(MATPBaseInfo_CommonBrakSpd).append("|")
                        .append(MATPBaseInfo_RunDistance).append("|")
                        .append(MATPBaseInfo_Direction).append("|")
                        .append(MATPBaseInfo_LineID).append("|")
                        .append(MATPBaseInfo_AtpError).append("|")
                        .append(weather)
                //....所有的55个字段，（实际项目中包含300多个字段），我们做的模块只涉及55个，因此截图55个字段

                ;


            }


            producer.send(new ProducerRecord<String, String>("train0706",stringBuffer.toString()));
        }

        sub.close();
        context.close();
    }
}
