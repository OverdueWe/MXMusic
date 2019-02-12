package com.mxnavi.gf.mvvm.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mxnavi.gf.mvvm.util.TUtil;

/**
 * describle : LifecycleFragment
 * @author Mark
 * @date 2018.11.30
 */

public abstract class BaseLifecycleFragment<T extends  BaseViewModel> extends BaseFragment {

    protected T mViewModel;

    /**
     * 状态通知
     */
    protected Observer stateObserver = new Observer<Integer>() {
        @Override
        public void onChanged(@Nullable Integer integer) {

        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        dataObserver();
    }

    protected <T extends ViewModel> T ViewModelProviders(BaseFragment fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);
    }

    public abstract void dataObserver();
}
