package com.example.miquan.ui.activitiy;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.miquan.R;
import com.example.miquan.databinding.ActivityImageBinding;
import com.example.miquan.ui.adapter.SimpleDraweeAdapteer;
import com.example.miquan.ui.detail.ImageViewModel;
import com.kelin.mvvmlight.bindingadapter.viewpager.ViewBindingAdapter;

import me.tatarka.bindingcollectionadapter.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

public class ImageActivity extends AppCompatActivity {

    public static final String RESOURCE_ID = "resource_id";
    public static final String IMG_INDEX = "image_index";
    ActivityImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        int id = getIntent().getIntExtra(RESOURCE_ID, 147);
        final int index = getIntent().getIntExtra(IMG_INDEX, 0);
        binding.switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(ImageActivity.this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                textView.setTextColor(getResources().getColor(R.color.white));
                return textView;
            }
        });
        binding.setImageViewModel(new ImageViewModel(this, id, index));
        BindingCollectionAdapters.setAdapter(binding.viewpager, ItemViewArg.of(binding.getImageViewModel().itemView), binding.getImageViewModel().viewModels, null, null);
        ViewBindingAdapter.onScrollChangeCommand(binding.viewpager, null, binding.getImageViewModel().itemSelected, null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.viewpager.setCurrentItem(index, false);
            }
        }, 100);
    }

    public void switchToolbar(){
        binding.getImageViewModel().visibleClick.execute();
    }

    public void showSelected(final int index) {
        binding.switcher.setText("" + (index + 1));
    }
}
