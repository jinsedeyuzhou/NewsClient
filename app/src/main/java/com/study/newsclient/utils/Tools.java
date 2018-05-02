package com.study.newsclient.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by Administrator on 2017/10/24.
 * 应用中用到的特定工具类
 */

public class Tools {

    /**
     * 设置倒计时时间
     */
    public static int setDateTime(String businessDate, String ForceDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        String currentTime = sdf.format(current);
        String carTime = "";

        //商业较强都都不为空
        if (!businessDate.isEmpty() && !ForceDate.isEmpty()) {
            carTime = ForceDate;
        }
        //交强险为空
        else if (!businessDate.isEmpty() && ForceDate.isEmpty()) {
            carTime = businessDate;
        }
        //商业为空,交强不为空
        else if (businessDate.isEmpty() && !ForceDate.isEmpty()) {
            carTime = ForceDate;
        } else if (businessDate.isEmpty() && ForceDate.isEmpty()) {
            carTime = "";

            return 0;

        }


        int betweenTime = 0;
        try {
            Date d1 = sdf.parse(currentTime);
            Date d2 = sdf.parse(carTime);
            betweenTime = daysBetween(d1, d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return betweenTime;


    }


    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days)) + 1;
    }

    /**
     * 得到json文件中的内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static Long getTimeStamp(String dateStr) {
        if (TextUtils.isEmpty(dateStr))
            return 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time = cal.getTimeInMillis();

        return (time - System.currentTimeMillis());
    }


    public static Long getStringTimeStampone(String str) {
        str=str.replace("/Date(","").replace(")/","");
        String timeStr = str.substring(0,str.length()-5);
        Date date = new Date(Long.parseLong(timeStr));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time = cal.getTimeInMillis();

        return (time - System.currentTimeMillis());
    }


    public static String getCountDown(Long times_remain) {
        times_remain /= 1000;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = times_remain.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }

        if (hour > 60) {
            day = hour / 24;
            hour = hour % 24;
        }
        String secondFormat = second > 0 ? String.valueOf(second) + "秒" : "0秒";
        String minuteFormat = minute > 0 ? String.valueOf(minute) + "分" : "0分";
        String hourFormat = hour > 0 ? String.valueOf(hour) + "时" : "0时";
        String dayFormat = day > 0 ? String.valueOf(day) + "天" : "";

        return dayFormat + hourFormat + minuteFormat + secondFormat;
    }

    /**
     * 对象相同属性copy
     *
     * @param obj
     * @param toResult
     * @return
     * @throws Exception
     *             转换报错
     */
    public static <T> T cloneObj(Object obj, Class<T> toResult) {
        if (obj == null) {
            return null;
        }
        try {
            T t = toResult.newInstance();
            Field[] fields = toResult.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);//修改访问权限
                if (Modifier.isFinal(field.getModifiers()))
                    continue;
                if (isWrapType(field)) {
                    String firstLetter = field.getName().substring(0, 1).toUpperCase(); // 首字母大写
                    String getMethodName = "get" + firstLetter + field.getName().substring(1);
                    String setMethodName = "set" + firstLetter + field.getName().substring(1);
                    Method getMethod = obj.getClass().getMethod(getMethodName);   //从源对象获取get方法
                    Method setMethod = toResult.getMethod(setMethodName, new Class[] { field.getType() }); //从目标对象获取set方法

                    //如果get 和 set方法都从一个对象中获取会出现object is not an instance of declaring class这个错误
                    //like: User{name} People{name}
                    //因为如果从源对象中获取，在setMethod.invoke调用的时候，虽然名字相同，会由于类的不同，导致
                    //调用的方法却不是目标对象中应有的方法。实际就是:getMethod = com.package.User.getName();setMethod = com.package.User.setName();
                    //而setMethod.invoke调用的结果就变成 People.setName() == People.(com.package.User.setName())
                    //这里的setName却不是People该有的，so 报错了
                    //同理,如果从目标对象中获取，在getMethod.invoke调用的时候也会出错。
                    //因此，对于getMethod和setMethod的获取应该根据源对象和目标对象区别对待。

                    //当然如果只是进行单独的对象复制，就不用担心会出现调用不属于本身的方法，也就不用区分对象get和set

                    Object value = getMethod.invoke(obj); // get 获取的是源对象的值
                    setMethod.invoke(t, new Object[] { value }); // set 设置的是目标对象的值
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 是否是基本类型、包装类型、String类型
     */
    private static boolean isWrapType(Field field) {
        String[] types = { "java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long",
                "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Char", "java.lang.String", "int",
                "double", "long", "short", "byte", "boolean", "char", "float" };
        List<String> typeList = Arrays.asList(types);
        return typeList.contains(field.getType().getName()) ? true : false;
    }


    /**
     * 判断recyclerView是否滑动到底部
     * @param recyclerView
     * @return
     */
    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (recyclerView.computeVerticalScrollRange() > recyclerView.computeVerticalScrollExtent() && visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 格式化时间选择器的时间
     * @param date
     * @return
     */
    public static String SIMPLE_YEAR_MONTH_DAY="yyyy-MM-dd";
    public static String SIMPLE_HOUR_MINUTE_SEC=" HH:mm:ss";
    public static String getTime(Date date,String simple) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(simple);
        return format.format(date);
    }




}
