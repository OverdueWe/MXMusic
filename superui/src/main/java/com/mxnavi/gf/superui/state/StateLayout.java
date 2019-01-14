package com.mxnavi.gf.superui.state;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.Map;

/**
 * 描述 ：StateView父布局 装载View
 *
 * @author Mark
 * @date 2018.01.14
 */
public class StateLayout extends FrameLayout {

    private Context mContext;

    private Map<Class<? extends BaseState>,BaseState> mStates;
    private Class<? extends BaseState> preState;
    private Class<? extends BaseState> curState;

    private StateOnClickListener onClickListener;

    public StateLayout(@NonNull Context context, StateOnClickListener listener) {
        super(context);
        mContext = context;
        onClickListener = listener;
        init();
    }

    private void init() {

    }

    public interface StateOnClickListener {

        /**
         * 状态View点击回调
         */
        void onStateClick();
    }
}
