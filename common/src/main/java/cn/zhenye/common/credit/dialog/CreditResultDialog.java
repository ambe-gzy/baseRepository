package cn.zhenye.common.credit.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import cn.zhenye.base.base.BaseFullScreenDialogFragment;
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
        mCreditTv.setText(String.format("恭喜你获得%d积分\n是否领取积分并查看商品详情？", mCredit));
        mBtnGetCredit.setText("不了，只领取积分");
        mGoodsDetailBtn.setText(String.format("查看详情", mCredit));
        mBtnGetCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mGoodsDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData(){
        if (mItem == null) {
            mBlankIv.setVisibility(View.VISIBLE);
            return;
        }
        Glide.with(getContext()).load(mItem.goodsPic).into(mAdItemIv);
    }

    public static CreditResultDialog showDialogNow(FragmentManager manager, long credit){
        CreditResultDialog dialog = new CreditResultDialog();
        dialog.mCredit = credit;
        dialog.show(manager,"credit_result_dialog");
        return dialog;
    }

    public static CreditResultDialog showDialogNow(FragmentManager manager, long credit, ZAdResponse.Item item) {
        CreditResultDialog dialog = new CreditResultDialog();
        dialog.mCredit = credit;
        dialog.mItem = item;
        dialog.show(manager,"credit_result_dialog");
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        //todo 获取积分，弹广告
        CreditManager.getInstance().increaseCredit(mCredit);
        super.onDismiss(dialog);
    }
}
