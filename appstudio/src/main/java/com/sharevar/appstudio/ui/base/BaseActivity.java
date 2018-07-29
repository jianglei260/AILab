package com.sharevar.appstudio.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;


/**
 * Created by jianglei on 2016/11/10.
 */

public class BaseActivity extends QMUIFragmentActivity {
    private IntentFilter filter = new IntentFilter();
    private boolean receiverRegisted = false;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onEvent(intent.getAction());
            onEvent(intent);
        }
    };

    protected void onEvent(Intent intent) {

    }

    protected void onEvent(String action) {
    }

    protected void registeEventAction(String action) {
        filter.addAction(action);
        registerReceiver(receiver, filter);
        receiverRegisted = true;
    }

    public void publishEvent(String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    public void publishEvent(Intent intent) {
        sendBroadcast(intent);
    }

    @Override
    protected int getContextViewId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiverRegisted)
            unregisterReceiver(receiver);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgId) {
        Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
