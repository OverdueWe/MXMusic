package com.mxnavi.gf.mvvm.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.mxnavi.gf.mvvm.rx.RxManager;

/**
 * describle : Base of ViewModel
 *
 * @author Mark
 * @date 2018.11.30
 */

public class BaseViewModel<T extends RxManager> extends AndroidViewModel {

    public T mRxManager;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRxManager != null) {
            mRxManager.unDisposable();
        }
    }
}
