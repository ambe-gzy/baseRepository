package cn.zhenye.drawer;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.common.constants.HttpUrlConstants;
import cn.zhenye.home.R;

public class PrivacyActivity extends ZyCommonActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        initToolbar((String) getResources().getText(R.string.activity_tittle_privacy));
        mWebView = findViewById(R.id.webview_privacy);
        mWebView.onResume();
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    findViewById(R.id.pb_privacy_loading).setVisibility(View.GONE);
                }
            }
        });

        mWebView.loadUrl(HttpUrlConstants.PRIVACY_POLICY_URL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
