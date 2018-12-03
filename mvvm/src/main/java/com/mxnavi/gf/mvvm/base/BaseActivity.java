package com.mxnavi.gf.mvvm.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.mxnavi.gf.autosize.internal.CustomAdapt;
import com.mxnavi.gf.mvvm.util.MxLog;

/**
 * describle : Base of Activity
 *
 * @author Mark
 * @date 2018.11.30
 */

public abstract class BaseActivity extends AppCompatActivity implements CustomAdapt{

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        setContentView(getLayoutId());
        initView(savedInstanceState);
        initToolBar();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MxLog.d(TAG,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MxLog.d(TAG,"onStop");
    }

    /**
     * 空实现此方法 避免Activity异常销毁后 造成画页重叠
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        MxLog.d(TAG,"onRestoreInstanceState");
    }

    /**
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选一个作为基准进行适配)
     *
     * @return {@code true} 为按照宽度适配, {@code false} 为按照高度适配
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    /**
     * 这里使用 IPhone 的设计图, IPhone 的设计图尺寸为 750px * 1334px, 高换算成 dp 为 667 (1334px / 2 = 667dp)
     * <p>
     * 返回设计图上的设计尺寸, 单位 dp
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return 设计图上的设计尺寸, 单位 dp
     */
    @Override
    public float getSizeInDp() {
        return 0;
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
