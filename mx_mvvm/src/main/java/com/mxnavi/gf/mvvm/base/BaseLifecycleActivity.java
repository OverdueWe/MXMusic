package com.mxnavi.gf.mvvm.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mxnavi.gf.mvvm.util.TUtil;

/**
 * describle : LifecycleActivity
 * @author Mark
 * @date 2018.11.30
 */

public abstract class BaseLifecycleActivity<T extends BaseViewModel> extends BaseActivity{

    protected T mViewModel;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        dataObserver();
    }

    protected <T extends ViewModel> T ViewModelProviders(AppCompatActivity activity, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(activity).get(modelClass);
    }

    public abstract void dataObserver();
}
