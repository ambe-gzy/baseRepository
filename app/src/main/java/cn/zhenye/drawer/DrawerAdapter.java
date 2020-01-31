package cn.zhenye.drawer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.base.tool.ZAppStoreUtils;
import cn.zhenye.home.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerHolder> {
    //列表数量
    private static final int ITEM_COUNT = 1;
    private Context mContext;
    @NonNull
    @Override
    public DrawerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        return new DrawerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerHolder holder, int position) {
        initDrawer(holder,position);
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    public class DrawerHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView message;

        public DrawerHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_drawer_icon);
            message = itemView.findViewById(R.id.tv_drawer_message);
        }
    }

    private void initDrawer(DrawerHolder holder, int position){
        switch (position){
            case 0:
                holder.icon.setImageResource(R.mipmap.ic_like);
                holder.message.setText(R.string.fragment_drawer_five_star);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("请到应用市场给我们评价！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mContext.startActivity(ZAppStoreUtils.getAppStoreIntent());
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();

                    }
                });
                break;
            case 1:
                break;
                default:

        }
    }
}
