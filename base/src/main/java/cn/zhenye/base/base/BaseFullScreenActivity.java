package cn.zhenye.base.base;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    private ImageView mToolbarReturn;

    private View mStatusbarBg;
    private View mStatusbarBg2;

    private View mCreditll;


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
        mStatusbarBg2 = super.findViewById(R.id.view_statusbar_bg2);
        mToolbarReturn = super.findViewById(R.id.v_toolbar_left);
        mToolbarTittle = super.findViewById(R.id.v_toolbar_middle);
        mToolbarCredit = super.findViewById(R.id.v_toolbar_right);
        mCreditll = super.findViewById(R.id.ll_credit);
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

    public void setCurrentCredit(String credit){
        mToolbarCredit.setText(credit);
    }

    public void setToolbarBg(@ColorRes int colorRes, boolean isShowCredit, @ColorRes int textColorRes, @DrawableRes int drawableRes){
        mStatusbarBg.setBackgroundResource(colorRes);
        mStatusbarBg2.setBackgroundResource(colorRes);

        mToolbarTittle.setTextColor(getResources().getColor(textColorRes));
        mToolbarReturn.setImageResource(drawableRes);
        mToolbarReturn.setImageTintMode(PorterDuff.Mode.DST);

        if (isShowCredit) {
            mCreditll.setVisibility(View.VISIBLE);
        } else {
            mCreditll.setVisibility(View.GONE);
        }
    }
}
