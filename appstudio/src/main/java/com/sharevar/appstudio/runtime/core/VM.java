package com.sharevar.appstudio.runtime.core;

import android.content.Context;

import com.sharevar.appstudio.R;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.common.string.StringUtil;
import com.sharevar.appstudio.object.Statement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VM {
    private List<Statement> statements;
    private Map<String, String> nameMap;
    private Context context;

    public static VM load(Context context) {
        VM vm = new VM();
        return vm;
    }

    public void run(List<Statement> statements) {
        this.statements = statements;
    }

    VM() {
        nameMap = new HashMap<>();
    }

    public List<String> getWords(String text) {
        List<String> strings = new ArrayList<>();
        char[] chars = text.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                if (builder.length() > 0) {
                    String string = builder.toString();
                    strings.add(string.toLowerCase());
                }
                builder.append(chars[i]);
            } else {
                builder.append(chars[i]);
            }
        }
        strings.add(builder.toString().toLowerCase());
        return strings;
    }

    public String getDisplayName(String name) {
        String displayName = nameMap.get(name);
        if (!StringUtil.isNullOrEmpty(displayName))
            return displayName;
        try {
            Field[] fields = R.string.class.getFields();
            List<String> words = getWords(name);
            if (words.size() > 1) {
                String joinName = StringUtil.join("_", words);
                displayName = findInFields(fields, joinName);
                if (displayName == null) {
                    StringBuilder builder = new StringBuilder();
                    for (String word : words) {
                        String splitName = findInFields(fields, word);
                        if (splitName != null) {
                            builder.append(splitName);
                        }
                    }
                    if (builder.length() > 0) {
                        displayName = builder.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNullOrEmpty(displayName)) {
            displayName = name;
        }
        nameMap.put(name, displayName);
        return displayName;
    }

    public String findInFields(Field[] fields, String name) {
        Field field = CollectionOP.findByAttr(fields, "name", name);
        if (field != null) {
            try {
                int value = (int) field.get(null);
                return context.getResources().getString(value);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Statement nextStep() {
        return null;
    }

    public void start() {

    }
}
