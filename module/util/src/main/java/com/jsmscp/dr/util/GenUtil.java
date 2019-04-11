package com.jsmscp.dr.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public abstract class GenUtil {

    public static String genPlatformCode() {
        return "D" + FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) +
                RandomStringUtils.randomNumeric(5);
    }

    public static String genImageCode() {
        return "I" + FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) +
                RandomStringUtils.randomNumeric(5);
    }

    public static String genDrugStoreToken() {
        String randomCode = "";
        // 用字符数组的方式随机
        String model = "^&*()_[]0123456789abcdefghijklmnopqistuvwxyzABCDEFGHIJKLMNOPQISTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 12; j++) {
            char c = m[(int) (Math.random() * 36)];
            // 保证六位随机数之间没有重复的
            if (randomCode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomCode = randomCode + c;
        }
        return randomCode;
    }
}
