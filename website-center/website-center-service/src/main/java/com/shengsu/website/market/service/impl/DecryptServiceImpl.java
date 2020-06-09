package com.shengsu.website.market.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.shengsu.website.market.entity.Decrypt;
import com.shengsu.website.market.entity.WeChatDecrypt;
import com.shengsu.website.market.service.DecryptService;
import com.shengsu.website.market.util.PKCS7Encoder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 15:24
 **/
@Service(value = "decryptService")
public class DecryptServiceImpl implements DecryptService {
    private static Charset CHARSET = Charset.forName("utf-8");
    /**
     * 对密文进行解密
     *
     * @param text 需要解密的密文
     *
     * @return 解密得到的明文
     *
     * @throws TpException 异常错误信息
     */
    @Override
    public String decrypt(Decrypt decrypt){
//            throws TpException {
        byte [] aesKey = Base64.decodeBase64(decrypt.getSessionKey() + "=");
        byte[] original = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encrypted = Base64.decodeBase64(decrypt.getText());
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            //throw new TpException(e);
            e.printStackTrace();
        }
        String xmlContent=null;
        String fromClientId;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和ClientId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);
            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            fromClientId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlContent;
    }
    /**
     * 还原4个字节的网络字节序
     *
     * @param orderBytes 字节码
     *
     * @return sourceNumber
     */
    private int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        int length = 4;
        int number = 8;
        for (int i = 0; i < length; i++) {
            sourceNumber <<= number;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    //解析电话号码
    @Override
    public String getPhoneNumber(WeChatDecrypt weChatDecrypt) {
        byte[] dataByte = Base64.decodeBase64(weChatDecrypt.getEncryptedData());

        byte[] keyByte = Base64.decodeBase64(weChatDecrypt.getSessionKey());

        byte[] ivByte = Base64.decodeBase64(weChatDecrypt.getIv());
        try {

            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                JSONObject obj=JSONObject.parseObject(result);
                String sphone=obj.get("phoneNumber").toString();
                return sphone;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
