package com.mxnavi.gf.superui.state;

import android.content.Context;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 描述 ： 状态View基类
 *
 * @author Mark
 * @date 2018.01.14
 */
public abstract class StateView implements Serializable {

    private static final String TAG = "StateView";

    private Context mContext;

    private StateRetryOnClickListener mRetryOnClickListener;

    private View rootView;

    public StateView(Context context) {
        mContext = context;
    }

    /**
     * 设置点击回调
     * @param listener
     */
    public void setRetryOnClickListener(StateRetryOnClickListener listener) {
        mRetryOnClickListener = listener;
    }

    public View getRootView() {
        if (rootView == null) {
            rootView = View.inflate(mContext, getLayoutID(), null);
        }
        return rootView;
    }

    public void onAttach() {

    }

    public void onDettach() {

    }

    /**
     * 获取布局
     *
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 重试
     */
    protected void retry() {
        if (mRetryOnClickListener != null) {
            mRetryOnClickListener.onRetryClick();
        }
    }

    public StateView copy() {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Object obj = null;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bao);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return (StateView) obj;
    }

    /**
     * 重试监听
     */
    public interface StateRetryOnClickListener {

        /**
         * 重试点击
         */
        void onRetryClick();
    }

}
