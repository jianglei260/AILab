package com.example.miquan.ui.activitiy;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.miquan.R;
import com.example.miquan.base.BaseActivity;
import com.example.miquan.databinding.ActivityLoginBinding;
import com.example.miquan.ui.login.LoginViewModel;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    public static final String ACTION_USER_LOGIN = "action_user_login";
    /**
     * 发送短信倒计时
     */
    public void showTimer() {
        final Button button = binding.e9;
        button.setText(65 + getString(R.string.timer_notify));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int time = 65;

            @Override
            public void run() {
                button.setText(time + getString(R.string.timer_notify));
                if (time > 0) {
                    handler.postDelayed(this, 1000);
                    button.setClickable(false);
                    binding.getLoginViewModel().getCodeClickable.set(false);
                } else {
                    button.setText(getString(R.string.get_sms_code));
                    button.setClickable(true);
                    binding.getLoginViewModel().getCodeClickable.set(true);
                }
                time--;
            }
        }, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new LoginViewModel(this);
        binding.setLoginViewModel(loginViewModel);
    }

    public void showWrongPhoneNumber() {
        binding.phoneNumber.setError(getString(R.string.error_phone_number));
    }

    public void onUserLogin(){
        publishEvent(LoginActivity.ACTION_USER_LOGIN);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
