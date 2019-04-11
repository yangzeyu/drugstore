package com.jsmscp.dr.util;

/**
 * string 工具类.
 */
@SuppressWarnings("unused")
public class StringUtils {
    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return (str == null || "".equals(str.trim()));
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean hasBlank(String... strs) {
        if (null == strs || 0 == strs.length) {
            return true;
        }


        for (String str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }

        return false;
    }

    public static Integer[] processPartyIds(String str) {
        String[] partyIds = str.split(",");
        Integer[] ids = new Integer[partyIds.length];
        for (int i = 0; i < partyIds.length; i++) {
            ids[i] = Integer.valueOf(partyIds[i]);
        }
        if (ids == null) {
            return null;
        }
        return ids;
    }
}
