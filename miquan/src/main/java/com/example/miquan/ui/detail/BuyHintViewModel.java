package com.example.miquan.ui.detail;

import android.content.Intent;

import com.example.miquan.base.ListItemViewModel;
import com.example.miquan.base.LoaddingViewModel;
import com.example.miquan.ui.activitiy.ChargeActivity;
import com.example.miquan.ui.activitiy.DetailActivity;
import com.kelin.mvvmlight.command.ReplyCommand;

import rx.functions.Action0;

public class BuyHintViewModel extends ListItemViewModel
{
    private DetailActivity activity;

    public BuyHintViewModel(DetailActivity activity) {
        this.activity = activity;
    }

    public ReplyCommand buyClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {

        }
    });
    public ReplyCommand vipClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            Intent intent=new Intent(activity, ChargeActivity.class);
            activity.startActivity(intent);
        }
    });

    @Override
    public int getViewType() {
        return VIEW_TYPE_LOAD_MORE;
    }
}
