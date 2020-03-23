package cn.zhenye.ad.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import cn.zhenye.ad.AdDetailActivity;
import cn.zhenye.ad.bean.AdDetailBean;
import cn.zhenye.base.tool.ZActivityUtils;
import cn.zhenye.common.ad.response.ZAdResponse;
import cn.zhenye.home.R;

public class ZAdAdapter extends RecyclerView.Adapter<ZAdAdapter.ViewHolder> {

    private Context mContext;
    private ZAdResponse mResponse;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_tbk_ad_goods_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        initView(holder, position);
    }

    @Override
    public int getItemCount() {
        if (mResponse == null || mResponse.items == null) {
            return 0;
        }
        return mResponse.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pictureIv;
        public TextView titleTv;
        public TextView endTimeTv;
        public TextView nowPriceTv;
        public TextView previewPriceTv;
        public TextView buttonTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureIv = itemView.findViewById(R.id.iv_tbk_ad_pic);
            titleTv = itemView.findViewById(R.id.tv_tbk_ad_goods_title);
            endTimeTv = itemView.findViewById(R.id.tv_tbk_ad_goods_end_time);
            nowPriceTv = itemView.findViewById(R.id.tv_tbk_ad_goods_now_price);
            previewPriceTv = itemView.findViewById(R.id.tv_tbk_ad_goods_preview_price);
            buttonTv = itemView.findViewById(R.id.btn_tbk_ad_goods);
        }
    }

    public void setData(ZAdResponse response) {
        if (response == null) {
            return;
        }
        mResponse = response;
        notifyDataSetChanged();
    }

    @SuppressLint("DefaultLocale")
    private void initView(@NonNull ViewHolder holder, int position) {
        if (mResponse == null || mResponse.items == null) {
            return;
        }

        ZAdResponse.Item childItem = mResponse.items.get(position);

        holder.titleTv.setText(childItem.goodsName);
        if (!TextUtils.isEmpty(childItem.goods_price) && !TextUtils.isEmpty(childItem.goods_discount)) {
            float primaryPrice = Float.parseFloat(childItem.goods_price);
            float discount = Float.parseFloat(childItem.goods_discount);
            float nowPrice = primaryPrice - discount;
            holder.nowPriceTv.setText(String.format("%.2f",nowPrice));
            holder.buttonTv.setText(mContext.getResources().getString(R.string.tbk_ad_btn_save, String.format("%.2f", discount)));

        }

        holder.previewPriceTv.setText(childItem.goods_price);
        holder.previewPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        if (!TextUtils.isEmpty(childItem.goodsPic)) {
            Glide.with(mContext).load(childItem.goodsPic).into(holder.pictureIv);
        }

        initEvent(holder, childItem);
    }

    private void initEvent(ViewHolder holder, final ZAdResponse.Item childItem) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdDetailActivity.class);
                AdDetailBean bean = new AdDetailBean();
                Bundle bundle = new Bundle();

                bean.title = childItem.goodsName;
                bean.num_iid = childItem.goodsId;
                bean.tkl = childItem.goods_youhuiquan;
                bundle.putParcelable(AdDetailActivity.KEY_BUNDLE, bean);
                intent.putExtras(bundle);
                ZActivityUtils.safeStartActivityWithIntent(mContext, intent);
            }
        });

    }
}
