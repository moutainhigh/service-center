package com.shengsu.website.market.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.shengsu.website.market.vo.AlipayDecryptVo;
import com.shengsu.website.market.entity.Decrypt;
import com.shengsu.website.market.entity.WeChatDecrypt;
import com.shengsu.website.market.service.DecryptService;
import com.shengsu.website.market.util.PKCS7Encoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;

import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_SHENGSU;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 15:24
 **/
@Slf4j
@Service(value = "decryptService")
public class DecryptServiceImpl implements DecryptService {
    @Value("${alipay.shengsu.decryptKey}")
    private String ssDecryptKey;
    @Value("${alipay.shengsu.publicKey}")
    private String ssSignVeriKey;
    @Value("${alipay.yuanshou.decryptKey}")
    private String ysDecryptKey;
    @Value("${alipay.yuanshou.publicKey}")
    private String ysSignVeriKey;

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
                JSONObject obj = JSONObject.parseObject(result);
                String sphone = obj.get("phoneNumber").toString();
                return sphone;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 敏感信息解密
     */
    @Override
    public String aliDecrypt(AlipayDecryptVo alipayDecryptVo) {
        String encryptContent = alipayDecryptVo.getResponse();
        Map<String, String> openapiResult = JSON.parseObject(encryptContent,
                new TypeReference<Map<String, String>>() {
                },
                Feature.OrderedField);
        String signType = StringUtils.defaultIfBlank(openapiResult.get("sign_type"), "RSA2");
        String charset = StringUtils.defaultIfBlank(openapiResult.get("charset"), "UTF-8");
        String encryptType = StringUtils.defaultIfBlank(openapiResult.get("encrypt_type"), "AES");
        String sign = openapiResult.get("sign");
        String content = openapiResult.get("response");

        log.info(String.format("准备验签和解密，sign=[%s], signType=[%s], encryptType=[%s], encryptContent=[%s]", sign, signType, encryptType, encryptContent));

        //如果密文的
        boolean isDataEncrypted = !content.startsWith("{");
        boolean signCheckPass = false;

        //2. 验签
        String signContent = content;

        //如果是加密的报文则需要在密文的前后添加双引号
        if (isDataEncrypted) {
            signContent = "\"" + signContent + "\"";
        }
        try {
            signCheckPass = AlipaySignature.rsaCheck(signContent, sign, SYSTEM_TAG_SHENGSU.equals(alipayDecryptVo.getSystemTag())?ssSignVeriKey:ysSignVeriKey, charset, signType);
        } catch (AlipayApiException e) {
            //验签异常, 日志
            log.error("验签异常，encryptContent=" + encryptContent, e);
        }
        if (!signCheckPass) {
            //验签不通过（异常或者报文被篡改），终止流程（不需要做解密）
            log.error("验签失败，encryptContent=" + encryptContent);
        }

        //3. 解密
        String plainData = null;
        if (isDataEncrypted) {
            try {
                plainData = AlipayEncrypt.decryptContent(content, encryptType, SYSTEM_TAG_SHENGSU.equals(alipayDecryptVo.getSystemTag())?ssDecryptKey:ysDecryptKey, charset);
            } catch (AlipayApiException e) {
                //解密异常, 日志
                log.error("解密异常，encryptContent=" + encryptContent, e);
            }
        } else {
            plainData = content;
        }
        return plainData;
    }
}
