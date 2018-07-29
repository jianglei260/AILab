package com.sharevar.miquan.ui.detail;

import android.content.Intent;

import com.sharevar.miquan.base.ListItemViewModel;
import com.sharevar.miquan.base.LoaddingViewModel;
import com.sharevar.miquan.ui.activitiy.ChargeActivity;
import com.sharevar.miquan.ui.activitiy.DetailActivity;
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
