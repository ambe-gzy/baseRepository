package cn.zhenye.dialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import cn.zhenye.base.tool.DateUtil;
import cn.zhenye.base.tool.PathUtil;
import cn.zhenye.base.tool.ThreadManager;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.common.db.dao.VoiceFileDao;
import cn.zhenye.common.db.entity.VoiceFileEntity;
import cn.zhenye.main.R;

public class CreateVoiceFileDialog extends DialogFragment implements View.OnClickListener,OnDismissListener {
    private View mView;

    private TextView mTvPositiveBtn;
    private TextView mTvNegativeBtn;
    private TextView mTvCreateTime;
    private EditText mEtFileName;
    private TextView mTvErrorMessage;

    private static boolean isShowing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setOnDismissListener(this);
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
        mTvErrorMessage = mView.findViewById(R.id.tv_voice_file_error_message);

        mTvPositiveBtn.setOnClickListener(this);
        mTvNegativeBtn.setOnClickListener(this);

        mTvCreateTime.setText(DateUtil.getCurDate(DateUtil.ACCURATE_TO_MIN));
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
                if (createVoiceFile()){
                    dismiss();
                }
                break;
        }
    }

    private boolean createVoiceFile() {
        String fileName = mEtFileName.getText().toString();
        if (TextUtils.isEmpty(fileName)){
            mTvErrorMessage.setText(getResources().getText(R.string.voice_file_error_file_name_null));
            return false;
        }else {
            //创建
            File filePath = new File(PathUtil.getCacheDir(getContext()),fileName);
            if (!filePath.exists()){
                if (filePath.mkdirs()){
                    saveToRoom(fileName,filePath.getAbsolutePath());
                    return true;
                }
                mTvErrorMessage.setText(getResources().getText(R.string.voice_file_error_file_create_fail));
                return false;
            }
            mTvErrorMessage.setText(getResources().getText(R.string.voice_file_error_file_exit));
            return false;
        }
    }

    private void saveToRoom(final String name, final String path){
        //保存
        ThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                VoiceFileDao dao = DatabaseManager.getInstance().voiceFileDao();
                VoiceFileEntity entity = new VoiceFileEntity(name, System.currentTimeMillis(),path,0);
                dao.saveVoiceFileEntity(entity);
            }
        });


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("aaa","dismiss");
        isShowing =false;
    }

    public static void CreateVoiceFileDialog(FragmentManager fragmentManager){
        if (isShowing){
            return;
        }
        isShowing = true;
        CreateVoiceFileDialog dialog = new CreateVoiceFileDialog();
        dialog.show(fragmentManager,"cool");
    }
}
