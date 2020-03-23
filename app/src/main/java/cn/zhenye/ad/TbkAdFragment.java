package cn.zhenye.ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.zhenye.ad.adapter.TbkAdAdapter;
import cn.zhenye.ad.vm.TbkVMManager;
import cn.zhenye.ad.vm.TbkViewModel;
import cn.zhenye.base.base.BaseFragment;
import cn.zhenye.home.R;
import cn.zhenye.widget.LoadingLayout;

public class TbkAdFragment extends BaseFragment {
    public static String KEY_BUNDLE = "key_bundle";
    public RecyclerView mRecyclerView;
    public TbkAdAdapter mTbkAdAdapter;
    private LoadingLayout mLoadingLayout;
    private View mEmptyTv;

    private int mFavId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tbk_ad,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initViewModel();
    }

    private void initViewModel() {
        TbkViewModel tbkViewModel = ViewModelProviders.of(this).get(TbkViewModel.class);
        tbkViewModel.getFavoriteItems().observe(this, new Observer<TbkDataFavoriteItem>() {
            @Override
            public void onChanged(TbkDataFavoriteItem response) {
                mLoadingLayout.setVisibility(View.GONE);
                if (response == null || response.response.getResult() == null) {
                    mEmptyTv.setVisibility(View.VISIBLE);
                } else {
                    if (response.favouritesId == mFavId) {
                        mTbkAdAdapter.setData(response.response.getResult());
                    }
                }
            }
        });
    }

    private void initView(){
        Bundle bundle = getArguments();
        if (bundle!= null){
            mFavId = bundle.getInt(KEY_BUNDLE);
            TbkVMManager.getInstance().loadFavoriteItems(mFavId);
        }
        mTbkAdAdapter = new TbkAdAdapter();

        mRecyclerView = getView().findViewById(R.id.rv_tbk_ad);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setAdapter(mTbkAdAdapter);

        mLoadingLayout = getView().findViewById(R.id.loading_bg);
        mEmptyTv = getView().findViewById(R.id.empty_bg);
    }


}
