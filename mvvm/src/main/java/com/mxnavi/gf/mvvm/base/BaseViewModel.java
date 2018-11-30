package com.mxnavi.gf.mvvm.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.mxnavi.gf.mvvm.util.TUtil;

/**
 * describle : Base of ViewModel
 *
 * @author Mark
 * @date 2018.11.30
 */

public class BaseViewModel<T extends BaseModel> extends AndroidViewModel {

    public T mModel;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mModel = TUtil.getNewInstance(this,0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mModel != null) {
            mModel.unDisposable();
        }
    }
}
