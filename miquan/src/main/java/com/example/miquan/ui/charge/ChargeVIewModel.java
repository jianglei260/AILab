package com.example.miquan.ui.charge;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.example.miquan.R;
import com.example.miquan.base.LoaddingViewModel;
import com.example.miquan.data.UserRepository;
import com.example.miquan.data.server.RemoteDataSource;
import com.example.miquan.model.PayResult;
import com.example.miquan.model.Price;
import com.example.miquan.ui.activitiy.ChargeActivity;
import com.example.miquan.ui.activitiy.LoginActivity;
import com.example.miquan.ui.widget.CustomToast;
import com.example.miquan.utils.IOTask;
import com.example.miquan.utils.RxUtil;
import com.example.miquan.utils.UIAction;
import com.kelin.mvvmlight.command.ReplyCommand;

import org.json.JSONException;
import org.json.JSONObject;

import rx.functions.Action0;

public class ChargeVIewModel extends LoaddingViewModel {
    private static final String TAG = "ChargeVIewModel";
    private ChargeActivity activity;
    public static final int TYPE_MONTH = 1;
    public static final int TYPE_QUARTER = 2;
    public static final int TYPE_YEAR = 3;
    public ObservableField<String> monthPrice = new ObservableField<>("20元/月");
    public ObservableField<String> quarterPrice = new ObservableField<>("40元/季度");
    public ObservableField<String> yearPrice = new ObservableField<>("120元/年");

    public ReplyCommand monCharge = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            checkLoginAndCharge(TYPE_MONTH);
        }
    });
    public ReplyCommand quarterCharge = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            checkLoginAndCharge(TYPE_QUARTER);
        }
    });

    public ReplyCommand yearCharge = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            checkLoginAndCharge(TYPE_YEAR);
        }
    });

    public void checkLoginAndCharge(final int type) {
        loadding.set(true);
        RxUtil.execute(new IOTask<Boolean>() {
            @Override
            public Boolean run() {
                return UserRepository.Companion.getInstance().isLogin(activity);
            }
        }, new UIAction<Boolean>() {
            @Override
            public void onComplete(Boolean login) {
                loadding.set(false);
                if (login) {
                    doCharge(type);
                } else {
                    gotoLogin();
                }
            }
        });
    }

    public void doCharge(final int type) {
        loadding.set(true);
        RxUtil.execute(new IOTask<String>() {
            @Override
            public String run() {
                String token = UserRepository.Companion.getInstance().getLocalToken(activity).getToken();
                String result = UserRepository.Companion.getInstance().charge(token, type);
                return result;
            }
        }, new UIAction<String>() {
            @Override
            public void onComplete(String result) {
                loadding.set(false);
                Log.d(TAG, "onComplete: " + result);
                if (TextUtils.isEmpty(result)) {
                    activity.showToast("获取订单出错");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject payObject = json.getJSONObject("data");
                    String payInfo = payObject.getString("payInfo");
                    if (!TextUtils.isEmpty(payInfo)) {
                        alipay(payInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void alipay(final String payInfo) {
        RxUtil.execute(new IOTask<String>() {
            @Override
            public String run() {
                PayTask alipay = new PayTask(activity);
                return alipay.pay(payInfo, true);
            }
        }, new UIAction<String>() {
            @Override
            public void onComplete(String s) {
                PayResult payResult = new PayResult(s);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    activity.showToast("支付成功");
                    onSendOrderSuccess();
                    CustomToast.showSuccess(activity, "支付成功");
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        activity.showToast("支付结果确认中");
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        CustomToast.showFailed(activity, "支付失败");
                    }
                }
            }
        });
    }

    public void onSendOrderSuccess() {

    }

    public void gotoLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public ChargeVIewModel(ChargeActivity activity) {
        this.activity = activity;
        RxUtil.execute(new IOTask<Price>() {
            @Override
            public Price run() {
                return RemoteDataSource.Companion.getInstance().priceConfig();
            }
        }, new UIAction<Price>() {
            @Override
            public void onComplete(Price price) {
                if (price != null) {
                    monthPrice.set(price.getMonth() + "元/月");
                    quarterPrice.set(price.getQuarter() + "元/季度");
                    yearPrice.set(price.getYear() + "元/年");
                }
            }
        });
    }

    @Override
    public void onBack() {
        activity.finish();
    }
}
