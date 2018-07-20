package com.sharevar.iconstudio.dev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DevLayout extends FrameLayout {
    public DevLayout(@NonNull Context context) {
        super(context);
    }

    public DevLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        String path="/Users/jianglei/AILab/iconstudio/src/main/java/com/sharevar/iconstudio/dev/log.txt";
        try {
            FileOutputStream out=new FileOutputStream(path);
            PrintStream ps=new PrintStream(out);
            System.setOut(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("hahha");
    }
}
