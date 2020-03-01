package cn.zhenye.ad;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import cn.zhenye.ad.vm.TbkVMManager;
import cn.zhenye.ad.vm.TbkViewModel;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;
import cn.zhenye.home.R;

public class TbkAdActivity extends ZyCommonActivity {
    private ViewPager2 mViewPager2;
    private TbkViewPagerAdapter mTbkViewPagerAdapter;
    private View mLoading;
    private TbkViewModel mTbkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initViewpager();
        initData();
        initVM();
    }

    private void initVM() {
        mTbkViewModel = ViewModelProviders.of(this).get(TbkViewModel.class);
        mTbkViewModel.getFavorites().observe(this, new Observer<TbkFavoritesResponse>() {
            @Override
            public void onChanged(TbkFavoritesResponse tbkFavoritesResponse) {
                if (tbkFavoritesResponse != null) {
                    mTbkViewPagerAdapter.setData(tbkFavoritesResponse);
                } else {
                    //todo 告诉用户网络出错，请重试。
                }
            }
        });
    }

    private void initViewpager() {
        mViewPager2 = findViewById(R.id.vp_tbk_ad_viewpager2);
        mLoading = findViewById(R.id.pb_tbk_ad_loading);
        mTbkViewPagerAdapter = new TbkViewPagerAdapter();
        mViewPager2.setAdapter(mTbkViewPagerAdapter);
        mLoading.setVisibility(View.GONE);
    }

    private void initData() {
        TbkVMManager.getInstance().loadFavourites(1);
    }
}
