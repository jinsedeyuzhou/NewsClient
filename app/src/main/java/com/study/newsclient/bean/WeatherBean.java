package com.study.newsclient.bean;

/**
 * Created by Administrator on 2017/12/22.
 */

public class WeatherBean {
    //weatherinfo需要对应json数据的名称，我之前随便写了个，被坑很久
    private Weatherinfo weatherinfo;
    public Weatherinfo getWeatherinfo() {
        return weatherinfo;
    }
    public void setWeatherinfo(Weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
    //city、cityid必须对应json数据的名称，不然解析不了
    public class Weatherinfo {
        private String city;
        private String cityid;
        private String temp;
        private String WD;
        private String WS;
        private String SD;
        private String WSE;
        private String time;
        private String isRadar;
        private String Radar;
        private String njd;
        private String qy;
        //这里省略get和set方法
    }
}
