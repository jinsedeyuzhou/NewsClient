package com.ebrightmoon.retrofitrx.recycle;

import org.reactivestreams.Subscription;

/**
 * Created by wyy on 2018/4/26.
 */

public interface RxActionManager<T> {

    void add(T tag, Subscription subscription);
    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}
