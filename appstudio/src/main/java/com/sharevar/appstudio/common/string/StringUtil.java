package com.sharevar.appstudio.common.string;

import java.util.Objects;


public class StringUtil {
    public static String lowerFirstChar(String content) {
        if (isNullOrEmpty(content))
            return content;
        char[] chars = content.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
    public static String join(CharSequence delimiter,
                              Iterable<? extends CharSequence> elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs: elements) {
            joiner.add(cs);
        }
        return joiner.toString();
    }
    public static boolean isNullOrEmpty(String content) {
        return content == null ? true : content.length() <= 0;
    }
}
