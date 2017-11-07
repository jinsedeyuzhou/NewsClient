package com.study.newsclient.ebus;


import com.yuxuan.common.ebus.IBus;
import com.yuxuan.common.ebus.IEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @Description: 如果用EventBus实现必须在注册类 写接收的方法
 * 如果不写会提示如下错误 即使是抽象的超类也必须写
 * its super classes have no public methods with the @Subscribe
 */
public class EventBusImpl implements IBus {

    private ConcurrentMap<Object, EventBus> mEventCompositeMap = new ConcurrentHashMap<>();

    /**
     * 注册事件监听
     *
     * @param object
     */
    @Override
    public void register(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        if (!EventBus.getDefault().isRegistered(object))
            EventBus.getDefault().register(object);
    }

    /**
     * 取消事件监听
     *
     * @param object
     */
    @Override
    public void unregister(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        if (!EventBus.getDefault().isRegistered(object))
            EventBus.getDefault().unregister(object);
    }

    /**
     * 发送普通事件
     *
     * @param event
     */
    @Override
    public void post(IEvent event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    @Override
    public void postSticky(IEvent event) {
        EventBus.getDefault().postSticky(event);
    }
}
