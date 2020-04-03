/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.common;


public class NuomiConstants {

    

    public static final String SIGN_TYPE_RSA                  = "RSA";

    /**
     * sha256WithRsa 算法请求类型
     */
    public static final String SIGN_TYPE_RSA2                 = "RSA2";

    public static final String SIGN_ALGORITHMS                = "SHA1WithRSA";

    public static final String SIGN_SHA256RSA_ALGORITHMS      = "SHA256WithRSA";

    public static final String ENCRYPT_TYPE_AES               = "AES";

    public static final String APP_ID                         = "app_id";

    public static final String FORMAT                         = "format";

    public static final String METHOD                         = "method";
    
    public static final String NOP_PLATFORM 				  = "nop";

    public static final String OPENAPI_PLATFORM 			  = "openapi";


    public static final String VERSION                        = "version";

    public static final String SIGN                           = "sign";

    public static final String NUOMI_SDK                     = "nuomi_sdk";


    public static final String ENCRYPT_TYPE                   = "encrypt_type";
    
    
    /** UTF-8字符集 **/
    public static final String CHARSET_UTF8                   = "UTF-8";

    /** GBK字符集 **/
    public static final String CHARSET_GBK                    = "GBK";
    
    /** 默认时间格式 **/
    public static final String DATE_TIME_FORMAT               = "yyyy-MM-dd HH:mm:ss";
    
    /**  Date默认时区 **/
    public static final String DATE_TIMEZONE                  = "GMT+8";
    
    
    /**
     * 两个平台都需要的参数
     */
    public static final String APP_KEY					      = "app_key";
    
    public static final String PRIVATE_KEY   = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKh8FlYteSUg2ejB0MZ8prTno5xMU8ZO+XI9D4YAyTI6/mZYqD/VSKzLYS6OX/VTdlDRWp47TcX8/UEASwXklMKK+7w0qcXUyMQxEb8MNhEfDYYyXSoOODsjDw+tp/YEVLL5mqYvZMIHin4vJQkmtHeMC0KymkxiP9lEqFZkQ/0rAgMBAAECgYAqGEakZVwdMKYBB1uYG9Cy5iT0igVsSJL+Pc9BFbUu/ZpOC9yNhkZQFchED4JeEuo+qu/PFNYLn3fO47na0Q9xW85bap6bJ7Fg+M4Sv70jRa2U7u7qip29iwxhoi7ZC9LDAn64W5b9O9quqFnhRsnw63fCrtv6fWjkPByeL7yx8QJBANnl/Z2YFw2Hb08x6RKAaTo/VDgBsjMEcaDvJOcAT3apHLR8Xyf/WWYlkvtg9WeuY0i0/1RbFt8vjd/EPoM5iz0CQQDF8iQjB3YVM2AK4hAbi2U/IOR0LxsGkW+A6AdQ21G1up1JL/5izk250RINs/q4cC4BuUo811fi4b2KtpeTktCHAkEAiXwDTNd03DVzNw3v2JurSXRB6GCxll0ccQXTS+m3OdC0avD6fVsxhjD5D6F61iODQ5HodMpQoRv5MZI6O0QopQJBAL1z3Nxd9smhy0l/RlVmnDfIfn8o2p/1sJfa+62Ky/C4NHPo6Hue4st26+WLnerLC22A7ym+HZK75hKOYhLPUJ8CQFp95h4W1kUK2wpZ6roNFTOSKA4jqQn3xHSJ51xrF9CURWAG31wMmEDReG74z/1nnUGzwg1b6XV0eZZHRM7uen0=";
        
    /**
     * NOP平台相关的参数
     */
    public static final String NOP_APPID					  = "nop_appid";
    
    public static final String NOP_METHOD					  = "nop_method";
    
    public static final String NOP_SIGN						  = "nop_sign";
    
    public static final String NOP_TIMESTAMP				  = "nop_timestamp";


    public static final String BIZ_CONTENT_KEY                = "biz_content";

    
    /** SDK版本号 */
    public static final String SDK_VERSION                    = "nuomi-sdk-java-dynamicVersionNo";


}
