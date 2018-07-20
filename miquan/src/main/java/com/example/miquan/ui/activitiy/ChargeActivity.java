package com.example.miquan.ui.activitiy;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.miquan.R;
import com.example.miquan.base.BaseActivity;
import com.example.miquan.databinding.ActivityChargeBinding;
import com.example.miquan.ui.charge.ChargeVIewModel;

public class ChargeActivity extends BaseActivity {
    private ActivityChargeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charge);
        binding.setChargeViewModel(new ChargeVIewModel(this));
    }
}
