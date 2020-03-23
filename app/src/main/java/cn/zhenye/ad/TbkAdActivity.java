package cn.zhenye.ad;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import cn.zhenye.ad.adapter.TbkViewPagerAdapter;
import cn.zhenye.ad.vm.TbkVMManager;
import cn.zhenye.ad.vm.TbkViewModel;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;
import cn.zhenye.home.R;
import cn.zhenye.widget.LoadingLayout;

public class TbkAdActivity extends ZyCommonActivity {
    private ViewPager mViewPager;
    private TbkViewPagerAdapter mTbkViewPagerAdapter;
    private TbkViewModel mTbkViewModel;
    private LoadingLayout mLoadingLayout;
    private View mEmptyTv;
    private PagerSlidingTabStrip mTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbk_ad);
        initView();
        initData();
        initVM();
    }

    private void initVM() {
        mTbkViewModel = ViewModelProviders.of(this).get(TbkViewModel.class);
        mTbkViewModel.getFavorites().observe(this, new Observer<TbkFavoritesResponse>() {
            @Override
            public void onChanged(TbkFavoritesResponse tbkFavoritesResponse) {
                setStatusView(STATUS.SUCCESS);
                if (tbkFavoritesResponse != null) {
                    mTabStrip.setVisibility(View.VISIBLE);
                    List<Fragment> fragments = new ArrayList<>();
                    List<String> titles = new ArrayList<>();
                    List<TbkFavoritesResponse.ChildTbkFavorites> childTbkFavorites = tbkFavoritesResponse.getResult();
                    if (childTbkFavorites == null) {
                        // 告诉用户网络出错，请重试。
                        setStatusView(STATUS.EMPTY);
                        return;
                    }
                    int size = childTbkFavorites.size();
                    for (int i = 0;i<size;i++) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(TbkAdFragment.KEY_BUNDLE,childTbkFavorites.get(i).favoritesId);
                        TbkAdFragment tbkAdFragment = new TbkAdFragment();
                        tbkAdFragment.setArguments(bundle);
                        fragments.add(tbkAdFragment);
                        titles.add(childTbkFavorites.get(i).favoritesTitle);
                    }
                    mTbkViewPagerAdapter.setData(fragments,titles);
                } else {
                    // 告诉用户网络出错，请重试。
                    setStatusView(STATUS.EMPTY);
                }
            }
        });
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_tbk_ad_viewpager);
        mLoadingLayout = findViewById(R.id.loading_bg);
        mEmptyTv = findViewById(R.id.empty_bg);
        mTabStrip = findViewById(R.id.ad_tbk_tabs);

        mTbkViewPagerAdapter = new TbkViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTbkViewPagerAdapter);
        mTabStrip.setViewPager(mViewPager);

    }

    private void initData() {
        TbkVMManager.getInstance().loadFavourites(1);
        setToolbarBg(R.color.color_FEA715,false, R.color.white, R.mipmap.ic_toolbar_back);
        setToolbarTittle(getResources().getString(R.string.fragment_drawer_ad));
        getToolbarReturn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class STATUS {
        public static final int LOADING = 0;
        public static final int EMPTY = 1;
        public static final int SUCCESS = 2;
    }

    private void setStatusView(int status) {
        switch (status) {
            case STATUS.LOADING:
                mLoadingLayout.setVisibility(View.VISIBLE);
                mEmptyTv.setVisibility(View.GONE);
                break;
            case STATUS.EMPTY:
                mLoadingLayout.setVisibility(View.GONE);
                mEmptyTv.setVisibility(View.VISIBLE);
                break;
            case STATUS.SUCCESS:
                mLoadingLayout.setVisibility(View.GONE);
                mEmptyTv.setVisibility(View.GONE);
                break;
        }
    }
}
