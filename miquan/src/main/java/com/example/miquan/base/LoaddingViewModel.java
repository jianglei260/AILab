package com.sharevar.miquan.base;

import android.databinding.ObservableBoolean;

import com.kelin.mvvmlight.command.ReplyCommand;

import rx.functions.Action0;

/**
 * Created by jianglei on 2016/12/10.
 */

public abstract class LoaddingViewModel extends BaseViewModel implements Backable {
    public ObservableBoolean loadding = new ObservableBoolean(false);
    public ObservableBoolean emptyContent = new ObservableBoolean(false);
    public ReplyCommand onBackClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            LoaddingViewModel.this.onBack();
        }
    });
}
