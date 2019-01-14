package com.mxnavi.gf.superui.state;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 描述 ： 状态View基类
 * @author Mark
 * @date 2018.01.14
 */
public abstract class BaseState implements Serializable {

    private StateLayout.StateOnClickListener onClickListener;

    public void onAttach() {

    }

    public void onDettach() {

    }

    /**
     * 设置点击回调
     * @param listener
     */
    public void setOnClickListener(StateLayout.StateOnClickListener listener) {
        onClickListener = listener;
    }

    public BaseState copy() {
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
        return (BaseState)obj;
    }

}
