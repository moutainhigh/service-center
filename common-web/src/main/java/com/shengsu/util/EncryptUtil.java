package com.shengsu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class EncryptUtil
{

    private static final Logger logger = Logger.getLogger(EncryptUtil.class);

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f" };

    public static final String CHARSET_UTF8 = "UTF-8";

    public static String byteArrayToHexString(byte[] b)
    {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
        {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    public static String md5Encry(String strSrc) throws Exception
    {
        String returnStr = null;
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            returnStr = byteArrayToHexString(md5.digest(strSrc.getBytes()));
        } catch (Exception e)
        {
            throw new Exception(e);
        }

        if (returnStr == null)
        {
            throw new Exception("md5Encry null result");
        }

        return returnStr;
    }

    public static String encryptMD5(String data) throws IOException
    {
        byte[] bytes = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse)
        {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }

        return byte2hex(bytes);
    }

    public static byte[] encryptMD5(byte[] data) throws IOException
    {
        byte[] bytes = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse)
        {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }

        return bytes;
    }

    
    public static String encryptSHA(String data) throws IOException
    {
        byte[] bytes = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse)
        {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }

        return byte2hex(bytes);
    }

    public static String encryptHMAC(String data, String secret) throws IOException
    {
        byte[] bytes = null;

        try
        {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse)
        {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }

        return byte2hex(bytes);
    }

    private static String byte2hex(byte[] bytes)
    {
        StringBuilder sign = new StringBuilder();

        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1)
            {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }

    private static String getStringFromException(Throwable e)
    {
        String result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        e.printStackTrace(ps);

        try
        {
            result = bos.toString(CHARSET_UTF8);
        } catch (UnsupportedEncodingException ee)
        {
            logger.error(ee);
        }

        return result;
    }
}
