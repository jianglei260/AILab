package com.sharevar.appstudio.data;

import java.util.ArrayList;
import java.util.List;

public class Action extends BaseObject{
    private String type;
    private List<Action> dependencies=new ArrayList<>();
}
