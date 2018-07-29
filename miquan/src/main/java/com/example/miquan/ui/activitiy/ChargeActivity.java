package com.sharevar.miquan.ui.activitiy;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sharevar.miquan.R;
import com.sharevar.miquan.base.BaseActivity;
import com.sharevar.miquan.databinding.ActivityChargeBinding;
import com.sharevar.miquan.ui.charge.ChargeVIewModel;

public class ChargeActivity extends BaseActivity {
    private ActivityChargeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charge);
        binding.setChargeViewModel(new ChargeVIewModel(this));
    }
}
