/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.util;

import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.nuomi.common.NuomiConstants;
import com.shengsu.trade.pay.nuomi.util.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class NuomiSignature {




    public static String genSignWithRsa(Map<String, String> sortedParams, String privateKey) throws NuomiApiException{
    	
    	String sortedParamsContent = getSignContent(sortedParams);
    	
    	return rsaSign(sortedParamsContent, privateKey, NuomiConstants.CHARSET_UTF8);
    	
    }
   
    /**
     * 
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    


    /**
     * sha1WithRsa 加签
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws NuomiApiException
     */
    public static String rsaSign(String content, String privateKey,
                                 String charset) throws NuomiApiException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(NuomiConstants.SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                .getInstance(NuomiConstants.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw new NuomiApiException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            throw new NuomiApiException("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
                                                    InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = StreamUtil.readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * 签名验证
     *
     * @param sortedParams
     * @param pubKey
     * @param sign
     * @return
     * @throws NuomiApiException
     */
    public static boolean checkSignWithRsa(Map<String, String> sortedParams, String pubKey, String sign)
            throws NuomiApiException {
        String sortedParamsContent = getSignContent(sortedParams);
        return doCheck(sortedParamsContent, sign, pubKey, NuomiConstants.CHARSET_UTF8);
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     * @throws NuomiApiException
     */
    private static boolean doCheck(String content, String sign, String publicKey, String encode)
            throws NuomiApiException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(NuomiConstants.SIGN_TYPE_RSA);
            byte[] bytes = publicKey.getBytes();
            byte[] encodedKey = Base64.decodeBase64(bytes);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance(NuomiConstants.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));

            boolean bverify = signature.verify(Base64.decodeBase64(sign.getBytes()));
            return bverify;

        } catch (Exception e) {
            throw new NuomiApiException("验签==RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", e);
        }
    }
    


    

}
