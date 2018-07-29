package com.sharevar.miquan.ui.detail;

import android.databinding.ObservableField;

import com.sharevar.miquan.base.ListItemViewModel;

public class HeaderItemViewModel extends ListItemViewModel {
    public ObservableField<String> avataUrl = new ObservableField<>();
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> resNum = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();

    @Override
    public int getViewType() {
        return VIEW_TYPE_HEADER;
    }
}
