package com.sharevar.study.dev;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Proxy {
    public static void main(String[] args){
        try {
            FileOutputStream fos=new FileOutputStream("/Users/jianglei/AILab/study/src/main/java/com/sharevar/study/dev/log.txt");
            OutputStreamWriter writer=new OutputStreamWriter(fos);
            writer.write("haahhahha");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
