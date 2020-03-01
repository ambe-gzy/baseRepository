package cn.zhenye.home;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import cn.zhenye.base.tool.ZGsonUtils;
import cn.zhenye.base.tool.ZThreadManager;
import cn.zhenye.common.credit.VM.CreditStatusViewModel;
import cn.zhenye.common.tbad.TbAdManager;
import cn.zhenye.common.tbad.response.TbFavoritesResponse;
import cn.zhenye.home.adapter.CreditStatusAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.commonsdk.debug.E;

import java.io.IOException;
import java.util.List;


public class HomeCoinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_coin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView oneMinuteBtn = getView().findViewById(R.id.tv_credit_get_one_minute_btn);
        TextView fiveMinuteBtn = getView().findViewById(R.id.tv_credit_get_five_minute_btn);
        TextView fifteenMinuteBtn = getView().findViewById(R.id.tv_credit_get_fifteen_minute_btn);
        new CreditStatusAdapter().setBtn(oneMinuteBtn,fiveMinuteBtn,fifteenMinuteBtn)
                .setContext(getContext().getApplicationContext())
                .setVM(ViewModelProviders.of(getActivity()).get(CreditStatusViewModel.class))
                .setContainer(view)
                .init((AppCompatActivity) getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZThreadManager.getAds().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String jsonStr =  TbAdManager.getInstance().getTbFavourites(1);
                            if (jsonStr != null) {
                                Log.d(HomeCoinFragment.class.getSimpleName(),jsonStr);
                                TbFavoritesResponse response = ZGsonUtils.formJson(jsonStr,TbFavoritesResponse.class);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }
    }
}
