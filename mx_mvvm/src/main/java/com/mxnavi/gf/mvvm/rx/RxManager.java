package com.mxnavi.gf.mvvm.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * describle : Rx订阅管理类
 * @author Mark
 * @date 2018.11.30
 */

public class RxManager {

    private CompositeDisposable mCompositeDisposable;

    public RxManager() {
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
