package cn.zhenye.base.base;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cn.zhenye.base.R;
import cn.zhenye.base.tool.ZStatusbarUtils;

public abstract class BaseFullScreenActivity extends AppCompatActivity {

    private FrameLayout mWindowBackground;
    //布局容器
    private ViewGroup mContentWrapperView;
    //布局完成的容器
    private View mContentView = null;

    private TextView mToolbarTittle;
    private TextView mToolbarCredit;
    private View mToolbarReturn;

    private View mStatusbarBg;


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
        mStatusbarBg = super.findViewById(R.id.view_statusbar_bg);
        mToolbarReturn = super.findViewById(R.id.v_toolbar_left);
        mToolbarTittle = super.findViewById(R.id.v_toolbar_middle);
        mToolbarCredit = super.findViewById(R.id.v_toolbar_right);
        mStatusbarBg.setBackgroundResource(R.color.colorPrimary);
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ZStatusbarUtils.setWindowFullScreenWithStatusBar(getWindow(), true);
            //适配状态栏高度
            LinearLayout mWindowView = super.findViewById(R.id.ll_toolbar_and_wrapper_view);
            fitStatusBar(mWindowView);
        }
    }

    private void fitStatusBar(View view) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        lp.topMargin = ZStatusbarUtils.getStatusBarHeight(getApplicationContext());
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

    public void setToolbarTittle(CharSequence tittle){
        mToolbarTittle.setText(tittle);
    }

    public View getToolbarReturn(){
        return mToolbarReturn;
    }

    public void initToolbar(String tittle){
        mToolbarTittle.setText(tittle);
        mToolbarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbarCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
