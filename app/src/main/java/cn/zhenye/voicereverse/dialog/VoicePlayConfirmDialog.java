package cn.zhenye.voicereverse.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import cn.zhenye.base.base.BaseDialogFragment;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.fragment.VoiceConstants;

public class VoicePlayConfirmDialog extends BaseDialogFragment implements View.OnClickListener {
    private static boolean isShow = false;
    private TextView mTvMessage;
    private ClickListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_vocie_play_confirm,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.tv_voice_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_voice_ok).setOnClickListener(this);
        mTvMessage = view.findViewById(R.id.tv_voice_file_create_time);
        mTvMessage.setText(getResources().getString(R.string.dialog_voice_user_guide_message, VoiceConstants.RECORD_PER_CREDIT,VoiceConstants.SAVE_PER_CREDIT));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isShow = false;
    }

    public static void showDialog(FragmentManager manager,@Nullable ClickListener listener){
        if (isShow){
            return;
        }
        isShow = true;
        VoicePlayConfirmDialog dialog = new VoicePlayConfirmDialog();
        dialog.mListener = listener;
        dialog.show(manager,VoicePlayConfirmDialog.class.getCanonicalName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_voice_cancel:
                dismiss();
            case R.id.tv_voice_ok:
                if (mListener!=null){
                    mListener.onPositiveClick();
                }
                dismiss();
        }
    }

    public interface ClickListener{
        void onPositiveClick();
    }
}
