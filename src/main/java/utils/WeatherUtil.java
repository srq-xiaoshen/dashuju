package utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
* 通过第三方接口，传入经纬度，获取天气信息和湿度
*
* */
public class WeatherUtil {
    public static String getWeather(String lng,String lat){
        //定义变量 存储URL，存储秘钥
        String key = "74c929c478904c95afc2e1dad9b84807";
        String urlStr = "https://free-api.heweather.net/s6/weather/now?location="+lng+","+lat+"&key="+key;
        String result = "";
        try {
            //获取网络连接
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            //引入hadoop common依赖，转换输入流
            result = IOUtils.toString(inputStream);
            //测试天气接口返回结果
            //System.out.println(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseJson(result);
    }

    //解析返回的结果，输出我们想要的天气情况字段和湿度
    public static String parseJson(String result){
        String wea = "晴";
        String hum = "0";
        if(StringUtils.isNotEmpty(result)){
            //利用fastjson解析字符串类型的json
            JSONObject jsonObject = JSON.parseObject(result);
            //{"HeWeather6":[{"basic":{"cid":"CN101011600",
            // "location":"东城","parent_city":"北京","admin_area":"北京市",
            // "cnty":"中国","lat":"39.91754532","lon":"116.41875458","tz":"+8.00"},
            // "update":{"loc":"2020-11-11 10:00","utc":"2020-11-11 02:00"},
            // "status":"ok",
            // "now":{"cloud":"10","cond_code":"100","cond_txt":"晴","fl":"6","hum":"53",
            // "pcpn":"0.0","pres":"1027","tmp":"8","vis":"8","wind_deg":"0",
            // "wind_dir":"北风","wind_sc":"2","wind_spd":"6"}}]}

            //获取HeWeather6
            JSONArray heWeather6 = jsonObject.getJSONArray("HeWeather6");
            //获取数组中第一个（索引为0）的数据
            JSONObject object = heWeather6.getJSONObject(0);
            //获取now中的内容
            JSONObject now = object.getJSONObject("now");
            //获取天气和湿度以及风
            String cond_txt = now.getString("cond_txt");
            hum = now.getString("hum");
            String wind_dir = now.getString("wind_dir");
            if(StringUtils.isNotEmpty(cond_txt)){
                //判断是否包含雨
                if(cond_txt.contains("雨")) {
                    wea = "雨";
                }else if(cond_txt.contains("雪")){
                    wea = "雪";
                }else if(cond_txt.contains("冰雹")){
                    wea = "冰雹";
                }else if(cond_txt.contains("扬沙")){
                    wea = "扬沙";
                }else if(cond_txt.contains("浮尘")){
                    wea = "浮尘";
                }else {
                    if(StringUtils.isNotEmpty(wind_dir)){
                        wea = "风";
                    }
                }
            }
        }

        return wea +"|" + hum;
    }

    //自己测试用
    public static void main(String[] args) {
        System.out.println(getWeather("116.41","39.92"));
    }
}
