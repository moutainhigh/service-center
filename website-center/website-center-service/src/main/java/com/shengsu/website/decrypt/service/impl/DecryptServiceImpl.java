package com.shengsu.website.decrypt.service.impl;


import com.shengsu.website.decrypt.entity.Decrypt;
import com.shengsu.website.decrypt.service.DecryptService;
import com.shengsu.website.decrypt.util.PKCS7Encoder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
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
}
