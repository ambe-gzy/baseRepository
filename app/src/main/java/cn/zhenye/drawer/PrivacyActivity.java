package cn.zhenye.drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.ZPermissionUtils;
import cn.zhenye.common.constants.HttpUrlConstants;
import cn.zhenye.home.R;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

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
                Log.d("aa",newProgress+"");
            }
        });

        mWebView.loadUrl("http://120.79.15.35/voicereverse/index.html");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
