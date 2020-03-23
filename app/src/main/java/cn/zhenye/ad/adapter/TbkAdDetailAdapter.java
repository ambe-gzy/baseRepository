package cn.zhenye.ad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.zhenye.ad.bean.TbkAdDetailBean;
import cn.zhenye.common.tbad.response.TbkItemDetailResponse;
import cn.zhenye.home.R;

public class TbkAdDetailAdapter extends PagerAdapter {
    private TbkAdDetailBean mBean;
    private Context mContext;

    private List<View> mListViews;

    private TbkItemDetailResponse mRresponse;

    private TbkAdDetailAdapter() {}

    public TbkAdDetailAdapter(List<View> views,Context context,TbkAdDetailBean bean) {
        mListViews = views;
        mContext = context;
        mBean = bean;
    }

    public TbkAdDetailAdapter(List<View> views, Context context, TbkItemDetailResponse bean) {
        mListViews = views;
        mContext = context;
        mRresponse = bean;
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mListViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mListViews.get(position),position);
        ImageView imageView = mListViews.get(position).findViewById(R.id.iv_ad_detail);
        if (mBean != null) {
            Glide.with(mContext).load(mBean.pic_urls.get(position)).into(imageView);
        }
        if (mRresponse.getPics() != null) {
            Glide.with(mContext).load(mRresponse.getPics().get(position)).into(imageView);
        }

        return mListViews.get(position);
    }
}
