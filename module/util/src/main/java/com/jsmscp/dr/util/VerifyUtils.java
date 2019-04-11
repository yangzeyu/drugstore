package com.jsmscp.dr.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class VerifyUtils {

    public static String getSignature (String token, String timestamp, String nonce) {
        //排序后组合成一个字符串
        String[] strs = new String[]{token, timestamp, nonce };
        Arrays.sort(strs);
        String tmp = "";
        for (String str : strs) {
            tmp += str;
        }
        //sha1加密
        String tmpStr = sha1(tmp);
        //转换成小写字符
        tmpStr = tmpStr.toLowerCase();
        //返回
        return tmpStr;
    }

    private static String sha1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为十六进制数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
