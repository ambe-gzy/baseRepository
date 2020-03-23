package cn.zhenye.ad;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.zhenye.ad.adapter.TbkAdDetailAdapter;
import cn.zhenye.ad.bean.AdDetailBean;
import cn.zhenye.ad.vm.TbkVMManager;
import cn.zhenye.ad.vm.TbkViewModel;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.tbad.response.TbkItemDetailResponse;
import cn.zhenye.home.R;
import cn.zhenye.widget.LoadingLayout;

public class AdDetailActivity extends ZyCommonActivity {
    public static final String KEY_BUNDLE = "key_bundle";
    private AdDetailBean mBean;
    private TbkAdDetailAdapter mAdDetailAdapter;
    private TextView mTittleTv;
    private TextView mTaokoulingTv;
    private TextView mCopyTv;
    private LoadingLayout mLoadingLayout;

    private ViewPager mViewPager;

    private String mTaokouling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail_acitivity);
        initView();
        handleIntent();
        initVM();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        mBean = (AdDetailBean) bundle.get(KEY_BUNDLE);

        if (mBean == null) {
            return;
        }

        mTittleTv.setText(mBean.title);
        createTaokouling(mBean.tkl);
        mTaokouling = mBean.tkl;

    }

    private void initView() {
        mTittleTv = findViewById(R.id.tv_tbk_ad_detail_tittle);
        mTaokoulingTv = findViewById(R.id.tv_tbk_ad_detail_taokouling);
        mViewPager = findViewById(R.id.vp_tbk_ad_detail_viewpager);
        mCopyTv = findViewById(R.id.tv_tbk_ad_detail_copy_btn);
        mLoadingLayout = findViewById(R.id.loading_bg);

        setToolbarBg(R.color.color_FEA715, false, R.color.white, R.mipmap.ic_toolbar_back);

        setToolbarTittle(getResources().getString(R.string.tbk_ad_detail_title));

        getToolbarReturn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCopyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mTaokouling)) {
                    //复制淘口令
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Label", mTaokouling);
                    if (cm != null) {
                        cm.setPrimaryClip(clipData);
                    }
                    ZToastUtils.showShort(R.string.tbk_ad_taokouling_copy_success);
                } else {
                    // 提示用户暂无淘口令
                    ZToastUtils.showShort(R.string.tbk_ad_taokouling_null);
                }
            }
        });
    }

    private void initVM() {
        TbkViewModel viewModel = ViewModelProviders.of(this).get(TbkViewModel.class);
        viewModel.getItemDetailLiveData().observe(this, new Observer<TbkItemDetailResponse>() {
            @Override
            public void onChanged(TbkItemDetailResponse response) {
                mLoadingLayout.setVisibility(View.INVISIBLE);
                if (response.getPics() == null) {
                    return;
                }
                List<View> views = new ArrayList<>();
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                for (int i = 0; i < response.getPics().size(); i++) {
                    views.add(inflater.inflate(R.layout.item_ad_detail, null));
                }

                mAdDetailAdapter = new TbkAdDetailAdapter(views,getApplicationContext(),response);
                mViewPager.setAdapter(mAdDetailAdapter);
                mViewPager.setOffscreenPageLimit(views.size());

                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
        if (mBean == null) {
            return;
        }
        TbkVMManager.getInstance().loadItemDetail(mBean.num_iid);
    }

    private void createTaokouling(String url) {
        if (TextUtils.isEmpty(url)) {
            mTaokoulingTv.setText(getResources().getString(R.string.tbk_ad_taokouling_null));
        } else {
            mTaokoulingTv.setText(getResources().getString(R.string.tbk_ad_taokouling_data, url));
        }
    }

}
