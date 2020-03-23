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

import java.util.List;

import cn.zhenye.ad.TbkAdDetailActivity;
import cn.zhenye.ad.bean.TbkAdDetailBean;
import cn.zhenye.base.tool.ZActivityUtils;
import cn.zhenye.common.tbad.response.TbkFavoriteItemResponse;
import cn.zhenye.home.R;

public class TbkAdAdapter extends RecyclerView.Adapter<TbkAdAdapter.ViewHolder> {

    private Context mContext;
    private List<TbkFavoriteItemResponse.ChildItem> mChildItems;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_tbk_ad_goods_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        initView(holder, position);
    }

    @Override
    public int getItemCount() {
        return mChildItems != null?mChildItems.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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

    public void setData(List<TbkFavoriteItemResponse.ChildItem> childItems){
        mChildItems = childItems;
        notifyDataSetChanged();
    }

    @SuppressLint("DefaultLocale")
    private void initView(@NonNull ViewHolder holder, int position){
        TbkFavoriteItemResponse.ChildItem childItem = mChildItems.get(position);

        holder.titleTv.setText(childItem.title);
        holder.nowPriceTv.setText(childItem.finalPrice);
        holder.previewPriceTv.setText(childItem.reversePrice);
        holder.previewPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);

        if (!TextUtils.isEmpty(childItem.finalPrice) && !TextUtils.isEmpty(childItem.reversePrice)) {
            float finalPrice = Float.parseFloat(childItem.finalPrice);
            float reversePrice = Float.parseFloat(childItem.reversePrice);
            holder.buttonTv.setText(mContext.getResources().getString(R.string.tbk_ad_btn_save, String.format("%.2f", (reversePrice - finalPrice))));
        }

        holder.endTimeTv.setText(childItem.couponEndTime);

        if (!TextUtils.isEmpty(childItem.picUrl)) {
            Glide.with(mContext).load(childItem.picUrl).into(holder.pictureIv);
        }

        initEvent(holder,childItem);
    }

    private void initEvent(ViewHolder holder, final TbkFavoriteItemResponse.ChildItem childItem) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TbkAdDetailActivity.class);
                TbkAdDetailBean bean = new TbkAdDetailBean();
                Bundle bundle = new Bundle();

                bean.title = childItem.title;
                bean.couponClickUrl = childItem.couponClickUrl;
                if (childItem.small_pic_urls != null) {
                    bean.pic_urls = childItem.small_pic_urls.smallPic;
                }

                bundle.putParcelable(TbkAdDetailActivity.KEY_BUNDLE,bean);
                intent.putExtras(bundle);

                ZActivityUtils.safeStartActivityWithIntent(mContext,intent);
            }
        });

    }
}
