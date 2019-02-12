package com.mxnavi.gf.pojo.netpojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述 ：网络数据基类
 *
 * @author Mark
 * @date 2018.12.03
 */


public class BasePojo implements Parcelable {

    /**
     * 网络请求id
     */
    private int requestId = 0;
    /**
     * 返回状态码
     */
    private int error = 1000;

    /**
     * 用户标识
     */
    private String uuid = "";

    public BasePojo() {

    }

    public BasePojo(int requestId) {
        this.requestId = requestId;
    }

    public BasePojo(int requestId, int error) {
        this.requestId = requestId;
        this.error = error;
    }

    public static final Parcelable.Creator<BasePojo> CREATOR = new Parcelable.Creator<BasePojo>() {
        @Override
        public BasePojo createFromParcel(Parcel in) {
            return new BasePojo(in);
        }

        @Override
        public BasePojo[] newArray(int size) {
            return new BasePojo[size];
        }
    };

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * 获取错误码.
     *
     * @return the error
     */
    public int getError() {
        return error;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    /**
     * 设置错误码.
     *
     * @param error the error
     */
    public void setError(int error) {
        this.error = error;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestId);
        dest.writeInt(this.error);
        dest.writeString(this.uuid);
    }

    protected BasePojo(Parcel in) {
        this.requestId = in.readInt();
        this.error = in.readInt();
        this.uuid = in.readString();
    }

    @Override
    public String toString() {
        return "BasePojo{" +
                "requestId=" + requestId +
                ", error=" + error +
                ", uuid=" + uuid +
                '}';
    }
}
