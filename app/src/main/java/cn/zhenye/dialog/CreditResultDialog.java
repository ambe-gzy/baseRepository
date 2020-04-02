package cn.zhenye.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import cn.zhenye.ad.AdDetailActivity;
import cn.zhenye.ad.bean.AdDetailBean;
import cn.zhenye.ad.vm.ZAdVMManager;
import cn.zhenye.baichuan.base.BaiChuanManager;
import cn.zhenye.base.base.BaseFullScreenDialogFragment;
import cn.zhenye.base.tool.ZActivityUtils;
import cn.zhenye.base.tool.ZMathUtils;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.R;
import cn.zhenye.common.ad.response.ZAdResponse;
import cn.zhenye.common.credit.manager.CreditManager;

public class CreditResultDialog extends BaseFullScreenDialogFragment {
    private long mCredit;
    private TextView mBtnGetCredit;
    private ZAdResponse.Item mItem;
    private ImageView mAdItemIv;
    private ImageView mBlankIv;
    private TextView mGoodsDetailBtn;
    private TextView mCreditTv;
    private TextView mCurrentPriceTv;
    private TextView mPastPriceTv;

    private String mTaokouling;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_credit_result,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnGetCredit = view.findViewById(R.id.btn_get_credit);
        mAdItemIv = view.findViewById(R.id.iv_ad_goods_pic);
        mBlankIv = view.findViewById(R.id.iv_ad_goods_empty);
        mCreditTv = view.findViewById(R.id.tv_credit);
        mGoodsDetailBtn = view.findViewById(R.id.btn_goods_detail);
        mCurrentPriceTv = view.findViewById(R.id.tv_tbk_ad_goods_now_price);
        mPastPriceTv = view.findViewById(R.id.tv_tbk_ad_goods_preview_price);

        mCreditTv.setText(String.format("恭喜你获得%d积分\n是否领取双倍积分并查看商品详情？", mCredit));
        mBtnGetCredit.setText("不了，只领取单倍积分");
        mGoodsDetailBtn.setText(String.format("领取双倍积分", mCredit));
        mBtnGetCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mGoodsDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCredit = 2*mCredit;

                dismiss();

                BaiChuanManager.getInstance().openTb(mItem.goodsId,getActivity());
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getItemData();
        initData();
    }

    private void initData(){
        if (mItem == null) {
            mBlankIv.setVisibility(View.VISIBLE);
            return;
        }
        Glide.with(getContext()).load(mItem.goodsPic).placeholder(R.drawable.bg_goods).error(R.drawable.bg_btn_normal).into(mAdItemIv);
        mPastPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        if (!TextUtils.isEmpty(mItem.goods_price) && !TextUtils.isEmpty(mItem.goods_discount)) {
            float primaryPrice = Float.parseFloat(mItem.goods_price);
            float discount = Float.parseFloat(mItem.goods_discount);
            float nowPrice = primaryPrice - discount;
            mCurrentPriceTv.setText(String.format("￥%.2f",nowPrice));
            mPastPriceTv.setText(String.format("￥%.2f", primaryPrice));
            if (!TextUtils.isEmpty(mItem.goods_youhuiquan )) {
                mTaokouling = mItem.goods_youhuiquan;
            } else {
                mTaokouling = mItem.goods_taokouling;
            }
        }
    }

    public static CreditResultDialog showDialogNow(FragmentManager manager, long credit){
        CreditResultDialog dialog = new CreditResultDialog();
        dialog.mCredit = credit;
        dialog.show(manager,"credit_result_dialog");
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        //todo 获取积分，弹广告
        CreditManager.getInstance().increaseCredit(mCredit);
        super.onDismiss(dialog);
    }

    private void getItemData(){
        ZAdResponse response = ZAdVMManager.getInstance().getAdResponse(getContext());
        if (response == null) {
            return;
        }
        int position = ZMathUtils.getRandom(0,response.items.size());
        mItem = response.items.get(position);
    }
}
