package com.yuxuan.common.ebus;

/**
 */
public class BusManager {
    private static IBus innerBus;
    private static IBus externalBus;

    public static void setBus(IBus bus) {
        if (externalBus == null && bus != null) {
            externalBus = bus;
        }
    }

    public static IBus getBus() {
        if (innerBus == null) {
            synchronized (BusManager.class) {
                if (innerBus == null) {
                    if (externalBus != null) {
                        innerBus = externalBus;
                    } else {
                        innerBus = new RxBusImpl();
                    }
                }
            }
        }
        return innerBus;
    }
}
