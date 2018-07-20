package com.sharevar.study.entity;

import java.util.ArrayList;
import java.util.List;

public class Part extends Fragment{
    private List<Practice> practices=new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Practice> getPractices() {
        return practices;
    }

    public void setPractices(List<Practice> practices) {
        this.practices = practices;
    }


}
