/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.shengsu.trade.pay.nuomi.common;

public class NuomiApiException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String            errCode;
    private String            errMsg;

    public NuomiApiException() {
        super();
    }

    public NuomiApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public NuomiApiException(String message) {
        super(message);
    }

    public NuomiApiException(Throwable cause) {
        super(cause);
    }

    public NuomiApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}