package com.sharevar.miquan.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharevar.miquan.R;


/**
 * Created by jianglei on 2016/12/2.
 */

public class CustomToast {
    public static void showSuccess(Context context, String msg) {
        Toast toast = new Toast(context);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        ImageView imageView = (ImageView) root.findViewById(R.id.image);
        TextView textView = (TextView) root.findViewById(R.id.text);
        imageView.setImageResource(R.drawable.ic_success);
        textView.setText(msg);
        toast.setView(root);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showFailed(Context context, String msg) {
        Toast toast = new Toast(context);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        ImageView imageView = (ImageView) root.findViewById(R.id.image);
        TextView textView = (TextView) root.findViewById(R.id.text);
        imageView.setImageResource(R.drawable.ic_notify);
        textView.setText(msg);
        toast.setView(root);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
