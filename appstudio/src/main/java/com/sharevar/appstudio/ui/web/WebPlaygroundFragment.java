package com.sharevar.appstudio.ui.web;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.ui.base.BaseFragment;

public class WebPlaygroundFragment extends BaseFragment {
    WebView webView;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_web_playground, null);
        webView = root.findViewById(R.id.web_view);
        return root;
    }


    public void initWebView(){
        webView.setAccessibilityDelegate();
    }
}
