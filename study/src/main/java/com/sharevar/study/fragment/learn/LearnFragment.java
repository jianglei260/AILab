package com.sharevar.study.fragment.learn;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUIProgressBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.sharevar.study.R;
import com.sharevar.study.base.BaseFragment;
import com.sharevar.study.data.CourseRepository;
import com.sharevar.study.entity.FlatFragment;
import com.sharevar.study.entity.Fragment;
import com.sharevar.study.entity.ResponseData;
import com.sharevar.study.entity.Unit;
import com.sharevar.study.utils.IOTask;
import com.sharevar.study.utils.RxUtil;
import com.sharevar.study.utils.UIAction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

public class LearnFragment extends BaseFragment {
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.goal_image)
    ImageView goalImage;
    @BindView(R.id.rectProgressBar)
    QMUIProgressBar progressBar;
    @BindView(R.id.paper_layout)
    FrameLayout paperLayout;
    MultiTypeAdapter adapter;
    RecyclerView recyclerView;
    Fragment current;
    PagerSnapHelper helper;
    List<FlatFragment> flatFragments;
    Unit unit;
    int currentPosition = 0;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_learn, null);
        ButterKnife.bind(this, view);
        initTopBar();
        initRecyclerView();
        initData();
        return view;
    }

    private void initRecyclerView() {
        recyclerView = new RecyclerView(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MultiTypeAdapter();
        adapter.register(FlatFragment.class, new LearnItemViewBinder(this));
        recyclerView.setAdapter(adapter);
        paperLayout.addView(recyclerView);

        helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        if (current != null) {
            recyclerView.scrollToPosition(getPostion(current));
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = helper.findTargetSnapPosition(recyclerView.getLayoutManager(), dx, dy);
                currentPosition = position;
                progressBar.setProgress(position);
            }
        });
    }

    private void initData() {
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        RxUtil.execute(new IOTask<ResponseData<String>>() {
            @Override
            public ResponseData<String> run() {
                if (unit != null) {
                    return CourseRepository.getInstance().getContentFromFile(unit.getFileName());
                }
                return new ResponseData<>();
            }
        }, new UIAction<ResponseData<String>>() {
            @Override
            public void onComplete(ResponseData<String> s) {
                tipDialog.dismiss();
                if (!TextUtils.isEmpty(s.getData())) {
                    flatFragments = CourseRepository.getInstance().toFlatFragments(unit, s.getData());
                    adapter.setItems(flatFragments);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public boolean next() {
        int nextPosition = currentPosition + 1;
        if (nextPosition < flatFragments.size()) {
            recyclerView.scrollToPosition(nextPosition);
            return true;
        } else {
            return false;
        }
    }

    public void back(){
        popBackStack();
    }

    public void setCurrent(Fragment current) {
        this.current = current;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<FlatFragment> getFlatFragments() {
        return flatFragments;
    }

    public void setFlatFragments(List<FlatFragment> flatFragments) {
        this.flatFragments = flatFragments;
    }

    public int getPostion(Fragment fragment) {
        List items = adapter.getItems();
        if (fragment == null || items == null || items.isEmpty())
            return 0;
        for (int i = 0; i < items.size(); i++) {
            if (fragment.equals(((FlatFragment) items.get(i)).getSource()))
                return i;
        }
        return 0;
    }

    private void initTopBar() {
        if (flatFragments != null) {
            progressBar.setMaxValue(flatFragments.size());
        }
    }

}
