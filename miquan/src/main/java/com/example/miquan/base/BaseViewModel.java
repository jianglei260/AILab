package com.sharevar.miquan.base;

import android.databinding.ObservableBoolean;

/**
 * Created by jianglei on 2016/12/7.
 */

public class BaseViewModel {
    public ObservableBoolean loadding = new ObservableBoolean(false);

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onResume() {
    }

}
