package cn.zhenye.home;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.common.credit.CreditStatusViewModel;
import cn.zhenye.home.adapter.CreditAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mintegral.msdk.base.fragment.BaseFragment;


public class HomeCoinFragment extends BaseFragment {

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
        new CreditAdapter().setBtn(oneMinuteBtn,fiveMinuteBtn,fifteenMinuteBtn)
                .setContext(getContext().getApplicationContext())
                .setVM(ViewModelProviders.of(getActivity()).get(CreditStatusViewModel.class))
                .init((AppCompatActivity) getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }
    }
}
