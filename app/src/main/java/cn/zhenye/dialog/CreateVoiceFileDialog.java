package cn.zhenye.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import cn.zhenye.main.R;

public class CreateVoiceFileDialog extends DialogFragment implements View.OnClickListener {
    private View mView;

    private TextView mTvPositiveBtn;
    private TextView mTvNegativeBtn;
    private TextView mTvCreateTime;
    private EditText mEtFileName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mView = inflater.inflate(R.layout.dialog_voice_file_create,container,false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLayoutParams();
        initUI();
    }

    private void initUI() {
        mEtFileName = mView.findViewById(R.id.et_voice_file_create_file_name);
        mTvCreateTime = mView.findViewById(R.id.tv_voice_file_create_time);
        mTvNegativeBtn = mView.findViewById(R.id.tv_voice_file_cancel);
        mTvPositiveBtn = mView.findViewById(R.id.tv_voice_file_ok);

        mTvPositiveBtn.setOnClickListener(this);
        mTvNegativeBtn.setOnClickListener(this);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    private void initLayoutParams(){
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_voice_file_cancel:
                dismiss();
                break;
            case R.id.tv_voice_file_ok:
                dismiss();
                break;
        }
    }
}
