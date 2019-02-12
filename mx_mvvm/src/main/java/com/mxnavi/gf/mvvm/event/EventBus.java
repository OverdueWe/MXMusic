package com.mxnavi.gf.mvvm.event;

import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * describle : 事件总线
 *
 * @author Mark
 * @date 2018.11.30
 */

public class EventBus {

    private final ConcurrentHashMap<Object, EventBusData<Object>> mEventBusDatas;

    private EventBus() {
        mEventBusDatas = new ConcurrentHashMap<>();
    }

    private static class EventBusHolder {
        private static EventBus sInstance = new EventBus();
    }

    public static EventBus getInstance() {
        return EventBusHolder.sInstance;
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey) {
        return subscribe(eventKey, "");
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, String tag) {
        return (MutableLiveData<T>) subscribe(eventKey, tag, Object.class);
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, Class<T> tClass) {
        return subscribe(eventKey, null, tClass);
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, String tag, Class<T> tClass) {
        String key = mergeEventKey(eventKey, tag);
        if (!mEventBusDatas.containsKey(key)) {
            mEventBusDatas.put(key, new EventBusData<Object>(true));
        } else {
            EventBusData liveBusData = mEventBusDatas.get(key);
            liveBusData.isFirstSubscribe = false;
        }
        return (MutableLiveData<T>) mEventBusDatas.get(key);
    }

    public <T> MutableLiveData<T> postEvent(Object eventKey, T value) {
        return postEvent(eventKey, null, value);
    }

    public <T> MutableLiveData<T> postEvent(Object eventKey, String tag, T value) {
        MutableLiveData<T> mutableLiveData = subscribe(mergeEventKey(eventKey, tag));
        mutableLiveData.postValue(value);
        return mutableLiveData;
    }

    private String mergeEventKey(Object eventKey, String tag) {
        String mEventkey;
        if (!TextUtils.isEmpty(tag)) {
            mEventkey = eventKey + tag;
        } else {
            mEventkey = (String) eventKey;
        }
        return mEventkey;
    }

    public void clear(Object eventKey) {
        clear(eventKey, null);
    }

    public void clear(Object eventKey, String tag) {
        if (mEventBusDatas.size() > 0) {
            String mEventkey = mergeEventKey(eventKey, tag);
            mEventBusDatas.remove(mEventkey);
        }
    }
}
