package com.mxnavi.gf.mvvm.event;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * describle : 封装LiveData
 *
 * @author Mark
 * @date 2018.11.30
 */

public class EventBusData<T> extends MutableLiveData<T> {

    public boolean isFirstSubscribe;

    EventBusData(boolean isFirstSubscribe) {
        this.isFirstSubscribe = isFirstSubscribe;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        super.observe(owner, new ObserverWrapper<>(observer, isFirstSubscribe));
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        private Observer<T> observer;

        private boolean isChanged;

        private ObserverWrapper(Observer<T> observer, boolean isFirstSubscribe) {
            this.observer = observer;
            isChanged = isFirstSubscribe;
        }

        @Override
        public void onChanged(@Nullable T t) {
            if (isChanged) {
                if (observer != null) {
                    observer.onChanged(t);
                }
            } else {
                isChanged = true;
            }
        }
    }

}
