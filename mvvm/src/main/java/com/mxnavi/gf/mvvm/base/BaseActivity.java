package com.mxnavi.gf.mvvm.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * describle : Base of Activity
 *
 * @author Mark
 * @date 2018.11.30
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        setContentView(getLayoutId());
        initView(savedInstanceState);
        initToolBar();
    }

    /**
     * 空实现此方法 避免Activity异常销毁后 造成画页重叠
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 是否设置沉浸式菜单栏
     *
     * @return
     */
    public abstract boolean setImmersionBar();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    public abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 设置状态栏
     */
    @TargetApi(19)
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && setImmersionBar()) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置工具栏
     */
    private void initToolBar() {

    }
}
