package com.sharevar.miquan.ui.detail;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.sharevar.miquan.BR;
import com.sharevar.miquan.R;
import com.sharevar.miquan.base.ListItemViewModel;
import com.sharevar.miquan.base.LoaddingViewModel;
import com.sharevar.miquan.data.ResouceRepository;
import com.sharevar.miquan.data.UserRepository;
import com.sharevar.miquan.model.Resource;
import com.sharevar.miquan.model.ResourceDetail;
import com.sharevar.miquan.ui.activitiy.DetailActivity;
import com.sharevar.miquan.utils.IOTask;
import com.sharevar.miquan.utils.RxUtil;
import com.sharevar.miquan.utils.UIAction;
import com.kelin.mvvmlight.command.ReplyCommand;

import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import rx.functions.Action0;

public class DetailViewModel extends LoaddingViewModel {
    private int resourceId;
    private DetailActivity activity;
    public ObservableList<ListItemViewModel> viewModels = new ObservableArrayList();
    public ObservableField<String> avataUrl = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    private HeaderItemViewModel headerItemViewModel;
    public final ItemViewSelector<ListItemViewModel> itemView = new ItemViewSelector<ListItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, ListItemViewModel item) {
            switch (item.getViewType()) {
                case ListItemViewModel.VIEW_TYPE_IMAGE:
                    itemView.set(BR.itemViewModel, R.layout.list_item_image);
                    break;
                case ListItemViewModel.VIEW_TYPE_HEADER:
                    itemView.set(BR.itemViewModel, R.layout.list_item_header);
                    break;
                case ListItemViewModel.VIEW_TYPE_LOAD_MORE:
                    itemView.set(BR.itemViewModel, R.layout.list_item_load_more);
                    break;
            }

        }

        @Override
        public int viewTypeCount() {
            return 1;
        }
    };
    public ReplyCommand backClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            onBack();
        }
    });
    public ReplyCommand shareClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
        }
    });

    public DetailViewModel(int resourceId, DetailActivity activity) {
        this.resourceId = resourceId;
        this.activity = activity;
        initImages();
    }


    public void initImages() {
        loadData(resourceId, true);
        Resource resource = ResouceRepository.Companion.getInstance().getResourceFromCache(resourceId);
        avataUrl.set(resource.getAvatarUrl());
        title.set(resource.getTitle());
        headerItemViewModel = new HeaderItemViewModel();
        headerItemViewModel.avataUrl.set(resource.getAvatarUrl());
        headerItemViewModel.imageUrl.set(resource.getImg());
        headerItemViewModel.title.set(resource.getTitle());
        viewModels.add(headerItemViewModel);
    }

    public void loadData(final int id, boolean clear) {
        RxUtil.execute(new IOTask<ResourceDetail>() {
            @Override
            public ResourceDetail run() {
                String token = "";
                if (UserRepository.Companion.getInstance().isLogin(activity)) {
                    token = UserRepository.Companion.getInstance().getLocalToken(activity).getToken();
                }
                return ResouceRepository.Companion.getInstance().getResourceDetail(id, token);
            }
        }, new UIAction<ResourceDetail>() {
            @Override
            public void onComplete(ResourceDetail resourceDetail) {
                if (resourceDetail != null) {
                    List<String> images = resourceDetail.getContent();
                    headerItemViewModel.resNum.set(String.valueOf(resourceDetail.getLength()));
                    for (int i = 0; i < images.size(); i++) {
                        ImageItemViewModel imageItemViewModel = new ImageItemViewModel(images.get(i), activity, i);
                        viewModels.add(imageItemViewModel);
                    }
                    if (images.size() < resourceDetail.getLength()) {
                        BuyHintViewModel itemViewModel = new BuyHintViewModel(activity);
                        viewModels.add(itemViewModel);
                    }
                } else {

                }
            }
        });

    }

    @Override
    public void onBack() {
        activity.finish();
    }
}