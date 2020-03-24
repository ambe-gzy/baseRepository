package cn.zhenye.ad;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.zhenye.ad.adapter.ZAdAdapter;
import cn.zhenye.ad.vm.ZAdVMManager;
import cn.zhenye.ad.vm.ZAdViewModel;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.common.ad.response.ZAdResponse;
import cn.zhenye.home.R;
import cn.zhenye.common.ui.LoadingLayout;

public class AdActivity extends ZyCommonActivity {
    private ZAdViewModel mZAdViewModel;
    public RecyclerView mRecyclerView;
    public ZAdAdapter mZAdAdapter;
    private LoadingLayout mLoadingLayout;
    private View mEmptyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initToolbar();
        initUI();
        initRecyclerView();
        initVM();
        initData();
    }

    private void initToolbar(){
        setToolbarBg(R.color.color_FEA715,false, R.color.white, R.mipmap.ic_toolbar_back);
        setToolbarTittle(getResources().getString(R.string.fragment_drawer_ad));
        getToolbarReturn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUI() {
        mRecyclerView = findViewById(R.id.rv_tbk_ad);
        mLoadingLayout = findViewById(R.id.loading_bg);
        mEmptyTv = findViewById(R.id.empty_bg);
    }

    private void initRecyclerView() {
        mZAdAdapter = new ZAdAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mZAdAdapter);
    }

    private void initVM(){
        ViewModelProvider provider = new ViewModelProvider(this);
        mZAdViewModel = provider.get(ZAdViewModel.class);
        mZAdViewModel.getAdResponseMutableLiveData().observe(this, new Observer<ZAdResponse>() {
            @Override
            public void onChanged(ZAdResponse response) {
                mLoadingLayout.setVisibility(View.GONE);
                if (response == null || response.items == null) {
                    mEmptyTv.setVisibility(View.VISIBLE);
                } else {
                    mZAdAdapter.setData(response);
                }
            }
        });
    }

    private void initData() {
        ZAdVMManager.getInstance().loadAd(getApplicationContext());
    }
}
