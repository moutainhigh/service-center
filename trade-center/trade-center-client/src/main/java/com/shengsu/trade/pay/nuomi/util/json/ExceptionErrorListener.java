/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.shengsu.trade.pay.nuomi.util.json;

public class ExceptionErrorListener extends BufferErrorListener {
    
    public void error(String type, int col) {
        super.error(type, col);
        throw new IllegalArgumentException(buffer.toString());
    }
}
