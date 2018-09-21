package com.sharevar.appstudio.ui.entity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.data.Entity;
import com.sharevar.appstudio.data.Response;
import com.sharevar.appstudio.object.DynamicObject;
import com.sharevar.appstudio.persistent.DataRepository;
import com.sharevar.appstudio.persistent.remote.db.DataEngine;
import com.sharevar.appstudio.ui.base.BaseFragment;
import com.sharevar.appstudio.ui.base.BaseListFragment;
import com.sharevar.appstudio.ui.common.RecyclerViewAdapter;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;
import com.sharevar.appstudio.utils.IOTask;
import com.sharevar.appstudio.utils.RxUtil;
import com.sharevar.appstudio.utils.UIAction;

import java.util.List;

public class EntityFragment extends BaseListFragment {
    @Override
    protected View onCreateView() {
        View view = super.onCreateView();
        return view;
    }

    @Override
    public void bindData() {
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        adapter.register(Entity.class, new RecyclerViewBinder<Entity>() {
            @Override
            public void bind(RecyclerViewAdapter.ItemViewHolder viewHolder, Entity entity) {
                textView(R.id.name).setText(entity.getSimpleName());
            }

            @Override
            public int layoutRes() {
                return R.layout.list_item_entity;
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final DataRepository<Entity> repository = DataEngine.create(Entity.class);
        RxUtil.execute(new IOTask<List<Entity>>() {
            @Override
            public List<Entity> run() {
                return repository.findAll(false);
            }
        }, new UIAction<List<Entity>>() {
            @Override
            public void onComplete(List<Entity> entities) {
                adapter.setItems(entities);
                adapter.notifyDataSetChanged();
            }
        });


    }
}
