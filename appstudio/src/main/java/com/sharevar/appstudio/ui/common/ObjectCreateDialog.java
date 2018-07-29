package com.sharevar.appstudio.ui.common;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.sharevar.appstudio.data.BaseObject;

public class ObjectCreateDialog {
    public static Dialog create(Class<? extends BaseObject> clazz, Context context,String... attrs){
        Dialog dialog=new AlertDialog.Builder(context).create();
        return dialog;
    }
}
