package cn.zhenye.base.baseview;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cn.zhenye.base.R;
import cn.zhenye.base.tool.StatusbarUtil;

public abstract class BaseFullScreenActivity extends AppCompatActivity {

    private FrameLayout mWindowBackground;
    //布局容器
    private ViewGroup mContentWrapperView;
    //布局完成的容器
    private View mContentView = null;
    //toolbar
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_fullscreen);
        initView();
        initWindow();

    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(BaseFullScreenActivity.this);
        mContentView = inflater.inflate(layoutResID, mContentWrapperView, true);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return mContentView.findViewById(id);
    }

    private void initView() {
        mWindowBackground = super.findViewById(R.id.fl_window_background);
        mContentWrapperView = super.findViewById(R.id.fl_content_wrapper_view);
        mToolbar = super.findViewById(R.id.tl_toolbar);
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            StatusbarUtil.setWindowFullScreenWithStatusBar(getWindow(), true);
            //适配状态栏高度
            LinearLayout mWindowView = super.findViewById(R.id.ll_toolbar_and_wrapper_view);
            fitStatusBar(mWindowView);
        }
    }


    private void fitStatusBar(View view) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        lp.topMargin = StatusbarUtil.getStatusBarHeight(getApplicationContext());
        view.setLayoutParams(lp);
    }

    /**
     * 用于设置背景颜色
     *
     * @return
     */
    public FrameLayout getWindowBackgroundManager() {
        return mWindowBackground;
    }

    /**
     * 用于设置toolbar相关的属性
     * 可隐藏toolbar
     * @return
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }

}
