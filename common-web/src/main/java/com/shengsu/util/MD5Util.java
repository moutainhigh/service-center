package com.shengsu.util;

import java.security.MessageDigest;

public class MD5Util {
    private static final String ALGORITHM_MD5 = "MD5";
    private static final String CHARSET = "UTF-8";
	/**
	   * MD5加密(32位)
	   * 
	   * @param instr 要加密的字符串
	   * @return 返回加密后的字符串
	   */
  public final static String encoderByMd5With32Bit(String instr) {
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      if (instr != null && !"".equals(instr)) {
        byte[] strTemp = instr.getBytes();
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
          byte byte0 = md[i];
          str[k++] = hexDigits[byte0 >>> 4 & 0xf];
          str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }
  public final static String encoderByMd5With32BitUTF8(String instr) {
	    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	      if (instr != null && !"".equals(instr)) {
	        byte[] strTemp = instr.getBytes("UTF-8");
	        // MD5计算方法
	        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	        mdTemp.update(strTemp);
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char str[] = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	          byte byte0 = md[i];
	          str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	          str[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(str);
	      } else {
	        return null;
	      }
	    } catch (Exception e) {
	      return null;
	    }
	  }
  public static String md5(String origin){
      try {
          MessageDigest md5 = MessageDigest.getInstance(ALGORITHM_MD5);
          //使用UTF-8编码将origin字符串编码并保存到source字节数组
          byte[] source = origin.getBytes(CHARSET);
          md5.update(source);  
          byte[] tmp = md5.digest();  
          return bytesToHexString(tmp);
      } catch (Exception e) {
          throw new RuntimeException(origin+"不可加密！");
      }
  }
  /**
   * 将byte[]转换为十六进制字符串
   * @param bArray
   * @return
   */
 private static final String bytesToHexString(byte[] bArray) {
   StringBuffer sb = new StringBuffer(bArray.length);
   String sTemp;
   for (int i = 0; i < bArray.length; i++) {
    sTemp = Integer.toHexString(0xFF & bArray[i]);
    if (sTemp.length() < 2){
     sb.append(0);
    }
    sb.append(sTemp.toUpperCase());
   }
   return sb.toString();
}
  public static void main(String[] args) {
	System.out.println(MD5Util.encoderByMd5With32Bit("113.240.245.2341001Y001B20141226150355123456"));
}
}
