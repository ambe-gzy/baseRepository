package cn.zhenye.common.credit.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import cn.zhenye.base.base.BaseDialogFragment;
import cn.zhenye.common.R;
import cn.zhenye.common.credit.manager.CreditManager;

public class CreditResultDialog extends BaseDialogFragment {
    private long mCredit;
    private Button mBtnGetCredit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_credit_result,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnGetCredit = view.findViewById(R.id.btn_get_credit);
        mBtnGetCredit.setText(String.format("+%d", mCredit));
        mBtnGetCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

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
}
