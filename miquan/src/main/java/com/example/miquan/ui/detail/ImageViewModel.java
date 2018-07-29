package com.sharevar.miquan.ui.detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.util.Log;

import com.sharevar.miquan.R;
import com.sharevar.miquan.BR;
import com.sharevar.miquan.base.LoaddingViewModel;
import com.sharevar.miquan.data.ResouceRepository;
import com.sharevar.miquan.ui.activitiy.ImageActivity;
import com.kelin.mvvmlight.command.ReplyCommand;


import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by jianglei on 2016/12/14.
 * 图片查看相关交互逻辑
 */

public class ImageViewModel extends LoaddingViewModel {
    private static final String TAG = "ImageViewModel";
    private ImageActivity activity;
    private int resourceId = 173;
    private int index = 0;
    public final ItemViewSelector<PageItemViewModel> itemView = new ItemViewSelector<PageItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, PageItemViewModel item) {
            itemView.set(BR.itemViewModel, R.layout.page_item_image);
        }

        @Override
        public int viewTypeCount() {
            return 1;
        }
    };

    public ObservableList<PageItemViewModel> viewModels = new ObservableArrayList<>();
    public ObservableBoolean titleVisible = new ObservableBoolean(false);

    public ReplyCommand itemSelected = new ReplyCommand(new Action1() {
        @Override
        public void call(Object o) {
            int position = (int) o;
            activity.showSelected(position);
        }
    });

    public ReplyCommand backClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            onBack();
        }
    });
    public ReplyCommand visibleClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            titleVisible.set(!titleVisible.get());
        }
    });

    public ImageViewModel(ImageActivity activity, int resourceId, int index) {
        this.activity = activity;
        this.index = index;
        this.resourceId = resourceId;
        initImages(resourceId);
    }


    public void initImages(int id) {
        Log.d(TAG, "initImages: " + id);
        List<String> pictures = ResouceRepository.Companion.getInstance().getResourceDetailFromCache(id).getContent();
        for (int i = 0; i < pictures.size(); i++) {
            PageItemViewModel itemViewModel = new PageItemViewModel(activity, pictures.get(i));
            viewModels.add(itemViewModel);
        }
        itemSelected.execute(index);
    }

    @Override
    public void onBack() {
        activity.finish();
    }
}
