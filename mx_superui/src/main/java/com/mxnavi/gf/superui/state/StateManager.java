package com.mxnavi.gf.superui.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ： 状态view管理类
 *
 * @author Mark
 * @date 2018.01.14
 */
public class StateManager {

    private static final String TAG = "StateManager";

    private StateLayout mStateLayout;

    private ViewGroup parentView;

    private View contentView;

    public enum State {
        LOADING, EMPTY, ERROR, SUCCESS
    }

    private StateManager(Context context, Map<State, Class> stateViews, StateOnClickListener stateOnClickListener) {
        mStateLayout = new StateLayout(context, stateViews);
        mStateLayout.setStateOnClickListener(stateOnClickListener);
    }

    private void setContentView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The contentView must be not null");
        } else {
            this.contentView = view;
            ViewGroup.LayoutParams params = view.getLayoutParams();
            mStateLayout.setLayoutParams(params);
            parentView = (ViewGroup) view.getParent();
            parentView.addView(mStateLayout);
            mStateLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 加载状态
     *
     * @param state
     */
    public void loadState(State state) {
        switch (state) {
            case SUCCESS:
                contentView.setVisibility(View.VISIBLE);
                mStateLayout.setVisibility(View.GONE);
                break;
            default:
                contentView.setVisibility(View.GONE);
                mStateLayout.setVisibility(View.VISIBLE);
                mStateLayout.loadState(state);
                break;
        }
    }

    public static class Builder {

        private Context context;

        private View contentView;

        private Map<State, Class> stateViews = new HashMap<>();

        private StateOnClickListener stateOnClickListener;

        public Builder() {

        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        public <T extends StateView> Builder setErrorView(Class<T> stateview) {
            this.stateViews.put(State.ERROR, stateview);
            return this;
        }

        public <T extends StateView> Builder setLoadView(Class<T> stateview) {
            this.stateViews.put(State.LOADING, stateview);
            return this;
        }

        public <T extends StateView> Builder setEmptyView(Class<T> stateview) {
            this.stateViews.put(State.EMPTY, stateview);
            return this;
        }

        public Builder setStateOnClickListener(StateOnClickListener listener) {
            stateOnClickListener = listener;
            return this;
        }

        public StateManager build() {
            StateManager stateManager = new StateManager(context, stateViews, stateOnClickListener);
            stateManager.setContentView(contentView);
            return stateManager;
        }
    }

    public interface StateOnClickListener {

        /**
         * 状态View点击回调
         */
        void onStateClick(StateManager.State state);
    }

}
