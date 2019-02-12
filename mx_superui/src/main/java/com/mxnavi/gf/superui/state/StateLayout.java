package com.mxnavi.gf.superui.state;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：StateView父布局 装载View
 *
 * @author Mark
 * @date 2018.01.14
 */
public class StateLayout extends FrameLayout {

    private Context mContext;

    private Map<StateManager.State, StateView> mStateViews;

    private StateManager.State currentState = StateManager.State.SUCCESS;

    private StateManager.StateOnClickListener mStateOnClickListener;

    public StateLayout(@NonNull Context context, Map<StateManager.State, Class> stateViews) {
        super(context);
        mContext = context;
        mStateViews = new HashMap<>();
        init(stateViews);
    }

    private <T extends StateView> void init(Map<StateManager.State, Class> stateViews) {
        for (Map.Entry<StateManager.State, Class> entry : stateViews.entrySet()) {
            Class<T> stateView = entry.getValue();
            if (stateView == null) {
                throw new IllegalArgumentException("The stateview must be not null");
            }
            try {
                Constructor constructor = stateView.getConstructor(Context.class);
                StateView view = (StateView) constructor.newInstance(mContext);
                mStateViews.put(entry.getKey(), view);
                view.setRetryOnClickListener(mRetryOnClickListener);
                view.getRootView().setTag(entry.getKey());
                addView(view.getRootView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置点击回调
     *
     * @param listener
     */
    protected void setStateOnClickListener(StateManager.StateOnClickListener listener) {
        mStateOnClickListener = listener;
    }

    /**
     * 描述 ：加载状态
     *
     * @param state
     */
    public <T extends StateView> void loadState(final StateManager.State state) {
        if (onMainThred()) {
            loadStateView(state);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    loadStateView(state);
                }
            });
        }
    }

    /**
     * 加载StateView
     */
    private void loadStateView(StateManager.State state) {
        if (currentState == state) {
            return;
        }
        currentState = state;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (state == child.getTag()) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }

        for (Map.Entry<StateManager.State, StateView> entry : mStateViews.entrySet()) {
            if (state == entry.getKey()) {
                entry.getValue().onAttach();
            } else {
                entry.getValue().onDettach();
            }
        }
    }

    /**
     * 重试点击监听
     */
    private StateView.StateRetryOnClickListener mRetryOnClickListener = new StateView.StateRetryOnClickListener() {
        @Override
        public void onRetryClick() {
            if (mStateOnClickListener != null) {
                mStateOnClickListener.onStateClick(currentState);
                loadStateView(StateManager.State.LOADING);
            }
        }
    };

    /**
     * 是否在主线程
     *
     * @return
     */
    private boolean onMainThred() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
