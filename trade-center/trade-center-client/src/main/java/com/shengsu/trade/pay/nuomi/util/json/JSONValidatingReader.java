/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.shengsu.trade.pay.nuomi.util.json;

public class JSONValidatingReader extends JSONReader {
    public static final Object INVALID = new Object();
    private JSONValidator validator;
    
    public JSONValidatingReader(JSONValidator validator) {
        this.validator = validator;
    }
    
    public JSONValidatingReader(JSONErrorListener listener) {
        this(new JSONValidator(listener));
    }
    
    public JSONValidatingReader() {
        this(new StdoutStreamErrorListener());
    }

    public Object read(String string) {
        if (!validator.validate(string)) return INVALID;
        return super.read(string);
    }
}
