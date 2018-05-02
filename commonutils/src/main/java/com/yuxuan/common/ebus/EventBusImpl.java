package com.yuxuan.common.ebus;


import org.greenrobot.eventbus.EventBus;


/**
 * @Description: RxBus事件管理
 */
public class EventBusImpl implements IBus {

//    private ConcurrentMap<Object, EventBus> mEventCompositeMap = new ConcurrentHashMap<>();

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
        if (EventBus.getDefault().isRegistered(object))
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
