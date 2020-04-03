/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.common;


public class NuomiResponse  {

	protected String    errno;

    protected String    msg;

    protected Object 	data;

    public String getErrno() {
        return this.errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }


}
