package cn.zhenye.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import cn.zhenye.base.R;

public class RecyclerViewNormalItem extends LinearLayout {
    private ImageView mIvLeftIcon;
    private TextView mTvMessage;
    private ImageView mIvRightIcon;

    @ColorInt
    private int mMessageColor;
    private int mMessageSize;
    private String mMessage;
    private Drawable mLeftIconDrawable;
    private Drawable mRightIconDrawable;


    public RecyclerViewNormalItem(Context context) {
        super(context);
    }

    public RecyclerViewNormalItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public RecyclerViewNormalItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecyclerViewNormalItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, AttributeSet attr){
        View.inflate(context, R.layout.item_recycler_normal, RecyclerViewNormalItem.this);
        mIvLeftIcon = this.findViewById(R.id.iv_icon_left);
        mTvMessage = this.findViewById(R.id.tv_message);
        mIvRightIcon = this.findViewById(R.id.iv_icon_right);

        TypedArray ta = context.obtainStyledAttributes(attr,R.styleable.RecyclerNormalItemStyle,0,0);
        mMessage = ta.getString(R.styleable.RecyclerNormalItemStyle_message);
        mMessageColor = ta.getColor(R.styleable.RecyclerNormalItemStyle_messageColor, Color.BLACK);
        mMessageSize = ta.getDimensionPixelSize(R.styleable.RecyclerNormalItemStyle_messageSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,32,context.getResources().getDisplayMetrics()));
        mLeftIconDrawable = ta.getDrawable(R.styleable.RecyclerNormalItemStyle_leftIcon);
        mRightIconDrawable = ta.getDrawable(R.styleable.RecyclerNormalItemStyle_rightIcon);
        ta.recycle();

        initMessage(mTvMessage,mMessageColor,mMessageSize,mMessage);
        initLeftIcon(mIvLeftIcon,mLeftIconDrawable,context);
        initRightIcon(mIvRightIcon,mRightIconDrawable,context);
    }

    private void initMessage(TextView textView,int textColor,long textSize,String text){
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setText(text);
    }

    private void initLeftIcon(ImageView imageView, Drawable drawable,Context context){
        if (drawable == null){
            drawable = context.getResources().getDrawable(R.mipmap.ic_close);
        }
        imageView.setImageDrawable(drawable);
    }

    private void initRightIcon(ImageView imageView, Drawable drawable,Context context){
        if (drawable == null){
            drawable = context.getResources().getDrawable(R.mipmap.ic_page_in);
        }
        imageView.setImageDrawable(drawable);
    }

    public TextView getTvMessage(){
        return mTvMessage;
    }

    public ImageView getmIvLeftIcon(){
        return mIvLeftIcon;
    }

    public ImageView getmIvRightIcon(){
        return mIvRightIcon;
    }


}
