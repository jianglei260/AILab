package com.sharevar.appstudio.common.string;

public class StringUtil {
    public static String lowerFirstChar(String content) {
        if (isNullOrEmpty(content))
            return content;
        char[] chars = content.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public static boolean isNullOrEmpty(String content) {
        return content == null ? true : content.length() <= 0;
    }
}
