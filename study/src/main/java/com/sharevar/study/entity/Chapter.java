package com.sharevar.study.entity;

import java.util.ArrayList;
import java.util.List;

public class Chapter extends Fragment{

    private List<Part> parts=new ArrayList<>();


    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
