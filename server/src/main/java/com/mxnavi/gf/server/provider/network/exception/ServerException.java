package com.mxnavi.gf.server.provider.network.exception;

import okhttp3.Request;

/**
 * 描述 ： 服务器异常
 *
 * @author Mark
 * @date 2018.08.11
 */

public class ServerException extends RuntimeException {

    private Request request;

    public ServerException() {
    }

    public ServerException(String msg) {
        super(msg);
    }

    public ServerException(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
