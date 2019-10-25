package com.ebrightmoon.doraemonkit.kit.gpsmock;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglikun on 2019/4/2
 */
public class WifiHooker extends BaseServiceHooker {
    @Override
    public String getServiceName() {
        return Context.WIFI_SERVICE;
    }

    @Override
    public String getStubName() {
        return "android.net.wifi.IWifiManager$Stub";
    }

    @Override
    public Map<String, MethodHandler> getMethodHandlers() {
        Map<String, MethodHandler> methodHandlers = new HashMap<>();
        methodHandlers.put("getScanResults", new GetScanResultsMethodHandler());
        methodHandlers.put("getConnectionInfo", new GetConnectionInfoMethodHandler());
        return methodHandlers;
    }

    @Override
    public void replaceBinder(Context context, IBinder proxy) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return;
        }
        Class<?> wifiManagerClass = wifiManager.getClass();
        Field mServiceField = wifiManagerClass.getDeclaredField("mService");
        mServiceField.setAccessible(true);
        Class stub = Class.forName(getStubName());
        Method asInterface = stub.getDeclaredMethod(METHOD_ASINTERFACE, IBinder.class);
        mServiceField.set(wifiManager, asInterface.invoke(null, proxy));
        mServiceField.setAccessible(false);
    }

    public class GetScanResultsMethodHandler implements MethodHandler {

        @Override
        public Object onInvoke(Object originService, Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originService, args);
            }
            return new ArrayList<ScanResult>();
        }
    }

    public class GetConnectionInfoMethodHandler implements MethodHandler {

        @Override
        public Object onInvoke(Object originService, Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originService, args);
            }
            return null;
        }
    }
}