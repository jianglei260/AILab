package com.example.miquan.ui.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;

import com.example.miquan.base.LoaddingViewModel;
import com.example.miquan.ui.activitiy.ImageActivity;
import com.kelin.mvvmlight.command.ReplyCommand;

import rx.functions.Action0;
import rx.functions.Action1;

public class PageItemViewModel extends LoaddingViewModel {
    private ImageActivity activity;
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableBoolean loadfailed = new ObservableBoolean(false);
    public ObservableBoolean loading = new ObservableBoolean(true);

    public PageItemViewModel(ImageActivity activity, String url) {
        this.activity = activity;
        imageUrl.set(url);
        loading.set(true);
    }

    public ReplyCommand reload = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            imageUrl.set(new String(imageUrl.get()));
        }
    });
    public ReplyCommand switchToolbar = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            activity.switchToolbar();
        }
    });

    public ReplyCommand onSuccessCommand = new ReplyCommand<Bitmap>(new Action1<Bitmap>() {
        @Override
        public void call(Bitmap bitmap) {
            loading.set(false);
        }
    });

    public ReplyCommand<String> onFailureCommand = new ReplyCommand<String>(new Action1<String>() {
        @Override
        public void call(String s) {
            loadfailed.set(true);
        }
    });

    @Override
    public void onBack() {
        activity.finish();
    }
}
