package com.sharevar.miquan.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.sharevar.miquan.R;
import com.sharevar.miquan.base.LoaddingViewModel;
import com.sharevar.miquan.data.UserRepository;
import com.sharevar.miquan.model.User;
import com.sharevar.miquan.ui.activitiy.LoginActivity;
import com.sharevar.miquan.utils.IOTask;
import com.sharevar.miquan.utils.RxUtil;
import com.sharevar.miquan.utils.UIAction;
import com.kelin.mvvmlight.command.ReplyCommand;

import rx.functions.Action0;

public class LoginViewModel extends LoaddingViewModel {
    private LoginActivity activity;
    public ObservableField<String> phoneNumber = new ObservableField<>();
    public ObservableField<String> smsCode = new ObservableField<>("");
    public ObservableBoolean getCodeClickable = new ObservableBoolean(true);
    public ReplyCommand getCode = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (checkPhoneNumber()) {
                getSmsCode(phoneNumber.get());
                activity.showTimer();
            }
        }
    });
    public ReplyCommand regsite = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (checkPhoneNumber() && checkSMSCode()) {
                signUp();
            }
        }
    });

    public LoginViewModel(LoginActivity activity) {
        this.activity = activity;
    }

    public void signUp() {
        loadding.set(true);
        RxUtil.execute(new IOTask<User>() {
            @Override
            public User run() {
                return UserRepository.Companion.getInstance().login(phoneNumber.get(), smsCode.get(), activity.getApplicationContext());
            }
        }, new UIAction<User>() {
            @Override
            public void onComplete(User user) {
                loadding.set(false);
                if (user != null) {
                    activity.onUserLogin();
                    activity.finish();
                    activity.showToast(R.string.login_success);
                } else {
                    activity.showToast(R.string.login_failed);
                }
            }
        });
    }


    public void getSmsCode(final String phone) {
        loadding.set(true);
        RxUtil.execute(new IOTask<Boolean>() {
            @Override
            public Boolean run() {
                try {
                    Boolean result=UserRepository.Companion.getInstance().getSmsCode(phone);
                    return result;
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        }, new UIAction<Boolean>() {
            @Override
            public void onComplete(Boolean success) {
                loadding.set(false);
                if (success) {
                    activity.showToast(R.string.sended_sms_code);
                }else {

                }
            }
        });
    }

    public boolean checkPhoneNumber() {
        if (phoneNumber.get() != null && phoneNumber.get().length() == 11)
            return true;
        activity.showWrongPhoneNumber();
        return false;
    }

    public boolean checkSMSCode() {
        if (smsCode.get() != null && smsCode.get().length() >= 4)
            return true;
        Toast.makeText(activity, activity.getString(R.string.error_sms_code), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onBack() {
        activity.finish();
    }
}
